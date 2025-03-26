package com.example.ecomadmin.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "stores")
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String domainName;
    private String email;

    @Column(name = "store_status")
    private String storeStatus;
    @Column(name = "total_rating")
    private int totalRating;
    @Column(name = "monitoring_date")
    private LocalDate monitoringDate;
}
