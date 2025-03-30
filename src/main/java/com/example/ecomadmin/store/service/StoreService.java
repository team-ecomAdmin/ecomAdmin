package com.example.ecomadmin.store.service;

import com.example.ecomadmin.store.dto.StoreRequestDto;
import com.example.ecomadmin.store.dto.StoreResponseDto;
import com.example.ecomadmin.store.entity.Store;
import com.example.ecomadmin.store.reposiroty.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // csv파일을 데이터베이스에 입력
    @Transactional
    public void readAndSaveCsv() {
        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                getClass().getResourceAsStream("/shop.csv"),
                                StandardCharsets.UTF_8
                        )
                )
        ) {
            String line;
            boolean isFirst = true;
            while ((line = br.readLine()) != null) {
                if (isFirst) { // 첫 줄은 헤더
                    isFirst = false;
                    continue;
                }

                String[] fields = line.split(",");

//                if (fields.length < 5) continue; // 컬럼 개수 검증

                try{  Store stores = Store.builder()
                        .companyName(fields[1])
                        .domainName(fields[2])
                        .email(fields[3])
                        .storeStatus(fields[4])
                        .totalRating(Integer.parseInt(fields[5]))
                        .monitoringDate(LocalDate.parse(fields[6]))
                        .build();

                    storeRepository.save(stores);
                } catch (NumberFormatException nfe) {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 업체 리스트 조회 중 필터 기능
    @Transactional(readOnly = true)
    public List<StoreResponseDto> findAll(Integer totalRating, String storeStatus) {

        if (totalRating != null && (totalRating < 0 || totalRating > 3)) {
            throw new IllegalArgumentException("전체평가는 0점~3점 사이만 가능합니다.");
        }

        // 상위 10개만 조회
        Pageable pageable = PageRequest.of(0, 10);

        List<Store> stores = storeRepository.findAllByConditions(totalRating, storeStatus, pageable);
        return stores.stream()
                .map(store -> new StoreResponseDto(
                        store.getId(),
                        store.getCompanyName(),
                        store.getDomainName(),
                        store.getEmail(),
                        store.getStoreStatus(),
                        store.getTotalRating(),
                        store.getMonitoringDate()))
                .toList();
    }

    // Pageable 기반 업체 리스트 조회 (필터 2개 필수)
    @Transactional(readOnly = true)
    public Page<StoreResponseDto> findAllWithPaging(Integer totalRating, String storeStatus, Pageable pageable) {

        if (totalRating == null || storeStatus == null || storeStatus.isBlank()) {
            throw new IllegalArgumentException("전체평가와 업소상태는 필수값입니다.");
        }

        if (totalRating != null && (totalRating < 0 || totalRating > 3)) {
            throw new IllegalArgumentException("전체평가는 0점~3점 사이만 가능합니다.");
        }

        Page<Store> stores = storeRepository.findAllByConditionsWithPaging(totalRating, storeStatus, pageable);

        return stores.map(store -> new StoreResponseDto(
                store.getId(),
                store.getCompanyName(),
                store.getDomainName(),
                store.getEmail(),
                store.getStoreStatus(),
                store.getTotalRating(),
                store.getMonitoringDate()
        ));
    }

    // 데이터베이스 정보 수정
    @Transactional
    public Store updateStore(Long storeId, StoreRequestDto requestDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found: " + storeId));

        store.setCompanyName(requestDto.getCompanyName());
        store.setDomainName(requestDto.getDomainName());
        store.setEmail(requestDto.getEmail());
        store.setStoreStatus(requestDto.getStoreStatus());
        store.setTotalRating(requestDto.getTotalRating());

        return storeRepository.save(store);
    }

    // 스케줄러를 통한 csv파일 불러오기
    @Transactional
    public void collectAndSaveData() {
        log.info("OpenAPI 데이터 수집 시작");
        try (InputStream is = getClass().getResourceAsStream("/stores.csv")) {
            if (is == null) {
                log.error("CSV 파일을 찾을 수 없습니다.");
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader("id", "company_name", "domain_name", "email", "store_status", "total_rating", "monitoring_date")
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(br, csvFormat);
            List<Store> storeList = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                Store store = Store.builder()
                        .id(Long.parseLong(record.get("id")))
                        .companyName(record.get("company_name"))
                        .domainName(record.get("domain_name"))
                        .email(record.get("email"))
                        .storeStatus(record.get("store_status"))
                        .totalRating(Integer.parseInt(record.get("total_rating")))
                        .monitoringDate(LocalDate.parse(record.get("monitoring_date")))
                        .build();
                storeList.add(store);
            }
            csvParser.close();

            // 100건씩 배치 저장
            for (int i = 0; i < storeList.size(); i += 100) {
                int end = Math.min(i + 100, storeList.size());
                List<Store> batch = storeList.subList(i, end);
                storeRepository.saveAll(batch);
            }
            log.info("OpenAPI 데이터 수집 및 저장 완료. 총 {}건 저장됨.", storeList.size());
        } catch (Exception e) {
            log.error("데이터 수집 중 예외 발생", e);
        }
        log.info("OpenAPI 데이터 수집 완료");
    }


    // 스케줄러를 사용하여 주기적으로 OpenAPI 데이터 수집 실행 (예: 10초마다)
    @Scheduled(cron = "0 0 * * * *")
    public void scheduledCollectData() {
        collectAndSaveData();
    }
}
