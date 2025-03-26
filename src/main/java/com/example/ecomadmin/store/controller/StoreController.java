package com.example.ecomadmin.store.controller;

import com.example.ecomadmin.store.dto.StoreResponseDto;
import com.example.ecomadmin.store.entity.Store;
import com.example.ecomadmin.store.reposiroty.StoreRepository;
import com.example.ecomadmin.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreRepository storeRepository;
    private final StoreService storeService;

    // 업체 리스트조회중 필터 기능
    @GetMapping
    public ResponseEntity<List<StoreResponseDto>> findAll(
            @RequestParam(required = false) Integer totalRating,
            @RequestParam(required = false) String storeStatus) {
        return ResponseEntity.ok(storeService.findAll(totalRating, storeStatus));
    }

    // Pageable 기반 업체 리스트 조회
    @GetMapping("/pageable")
    public ResponseEntity<Page<StoreResponseDto>> findAllWithPaging(
            @RequestParam Integer totalRating,
            @RequestParam String storeStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
     return ResponseEntity.ok(storeService.findAllWithPaging(totalRating, storeStatus, pageable));
    }
}
