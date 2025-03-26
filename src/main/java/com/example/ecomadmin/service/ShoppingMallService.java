package com.example.ecomadmin.service;

import com.example.ecomadmin.entity.Stores;
import com.example.ecomadmin.repository.ShoppingMallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShoppingMallService {
    private final ShoppingMallRepository shoppingMallRepository;

    public void readAndSaveCsv() {
        try (
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(
                                getClass().getResourceAsStream("/seouls.csv"),
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

                if (fields.length < 5) continue; // 컬럼 개수 검증

                Stores stores = Stores.builder()
                        .companyName(fields[0])
                        .domainName(fields[1])
                        .email(fields[2])
                        .storeStatus(fields[3])
                        .totalRating(Integer.parseInt(fields[4]))
                        .monitoringDate(LocalDateTime.parse(fields[5]))
                        .build();

                shoppingMallRepository.save(stores);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
