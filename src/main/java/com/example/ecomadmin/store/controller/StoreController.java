package com.example.ecomadmin.store.controller;

import com.example.ecomadmin.store.dto.StoreRequestDto;
import com.example.ecomadmin.store.entity.StoreEntity;
import com.example.ecomadmin.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private StoreService storeService;

    @PutMapping("/{storeId}")
    public StoreEntity updateStore(
            @PathVariable Long storeId,
            @RequestBody StoreRequestDto requestDto
    ) {
        return storeService.updateStore(storeId, requestDto);
    }

}
