package com.example.ecomadmin.store.service;

import com.example.ecomadmin.store.dto.StoreResponseDto;
import com.example.ecomadmin.store.entity.Store;
import com.example.ecomadmin.store.reposiroty.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public StoreEntity updateStore(Long storeId, StoreRequestDto requestDto) {
        StoreEntity store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("Store not found: " + storeId));

        store.setCompanyName(requestDto.getCompanyName());
        store.setDomainName(requestDto.getDomainName());
        store.setEmail(requestDto.getEmail());
        store.setStoreStatus(requestDto.getStoreStatus());
        store.setTotalRating(requestDto.getTotalRating());

        return storeRepository.save(store);
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> findAll(Integer totalRating, String storeStatus) {

        if (totalRating != null && (totalRating < 0 || totalRating > 3)) {
            throw new IllegalArgumentException("전체평가는 0점~3점 사이만 가능합니다.");
        }

        // 상위 10개만 조회
        Pageable pageable = PageRequest.of(0,10);

        List<Store> stores =  storeRepository.findAllByConditions(totalRating, storeStatus, pageable);
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
}