package com.example.ecomadmin.store.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StoreResponseDto {

    private final Long id;
    private final String companyName;
    private final String domainName;
    private final String email;
    private final String storeStatus;
    private final int totalRating;
    private final LocalDate monitoringDate;

    public StoreResponseDto(Long id,
                            String companyName,
                            String domainName,
                            String email,
                            String storeStatus,
                            int totalRating,
                            LocalDate monitoringDate) {
        this.id = id;
        this.companyName = companyName;
        this.domainName = domainName;
        this.email = email;
        this.storeStatus = storeStatus;
        this.totalRating = totalRating;
        this.monitoringDate = monitoringDate;
    }
}
