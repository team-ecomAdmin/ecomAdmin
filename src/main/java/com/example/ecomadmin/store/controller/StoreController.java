package com.example.ecomadmin.store.controller;

import com.example.ecomadmin.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/collection")
    public String collectCsvData() {
        storeService.readAndSaveCsv();
        return "CSV 데이터가 DB에 저장되었습니다!";
    }
}
