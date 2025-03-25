package com.example.ecomadmin.store.service;

import com.example.ecomadmin.store.dto.StoreResponseDto;
import com.example.ecomadmin.store.entity.Store;
import com.example.ecomadmin.store.enums.StoreStatus;
import com.example.ecomadmin.store.reposiroty.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public List<StoreResponseDto> findAll(Integer totalRating, StoreStatus storeStatus) {


        if (totalRating != null && (totalRating < 0 || totalRating > 3)) {
            throw new IllegalArgumentException("전체평가는 0점~3점 사이만 가능합니다.");
        }

        List<Store> stores =  storeRepository.filterByRatingAndStatus(totalRating, storeStatus);

        return stores.stream()
                .map(store -> new StoreResponseDto(
                        store.getId(),
                        store.getCompanyName(),
                        store.getDomainName(),
                        store.getEmail(),
                        store.getStoreStatus().toString(),
                        store.getTotalRating(),
                        store.getMonitoringDate()))
                .toList();
    }
}
