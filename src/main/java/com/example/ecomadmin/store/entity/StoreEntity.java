package com.example.ecomadmin.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "stores")
public class StoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CSV로부터 매핑된 컬럼들
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "domain_name")
    private String domainName;

    private String email;

    @Column(name = "store_status")
    private String storeStatus;

    @Column(name = "total_rating")
    private Integer totalRating;

    @Column(name = "monitoring_data")
    private LocalDate monitoringData;


}
