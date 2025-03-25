package com.example.ecomadmin.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StoreRequestDto {

    private String companyName;

    private String domainName;

    private String email;

    private String storeStatus;

    private Integer totalRating;

    private LocalDate monitoringData;

}
