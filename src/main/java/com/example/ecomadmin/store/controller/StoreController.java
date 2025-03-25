package com.example.ecomadmin.store.controller;

import com.example.ecomadmin.store.dto.StoreResponseDto;
import com.example.ecomadmin.store.enums.StoreStatus;
import com.example.ecomadmin.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 업체 리스트 조회
    @GetMapping("/api/v1/stores")
    public ResponseEntity<List<StoreResponseDto>> findAll(
            @RequestParam(required = false) Integer totalRating,
            @RequestParam(required = false) StoreStatus storeStatus) {
        return ResponseEntity.ok(storeService.findAll(totalRating, storeStatus));
    }
}
