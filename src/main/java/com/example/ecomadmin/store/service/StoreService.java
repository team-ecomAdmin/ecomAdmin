package com.example.ecomadmin.store.service;

import com.example.ecomadmin.store.dto.StoreRequestDto;
import com.example.ecomadmin.store.entity.StoreEntity;
import com.example.ecomadmin.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}