package com.example.ecomadmin.controller;

import com.example.ecomadmin.service.ShoppingMallService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShoppingMallController {

    private final ShoppingMallService shoppingMallService;

    @PostMapping("/collection")
    public String collectCsvData() {
        shoppingMallService.readAndSaveCsv();
        return "CSV 데이터가 DB에 저장되었습니다!";
    }
}
