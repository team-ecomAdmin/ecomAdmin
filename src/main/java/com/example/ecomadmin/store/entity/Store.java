package com.example.ecomadmin.store.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "stores")
@Builder
@AllArgsConstructor
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "domain_name")
    private String domainName;
    private String email;

    @Column(name = "store_status")
    private String storeStatus;
    @Column(name = "total_rating")
    private int totalRating;
    @Column(name = "monitoring_date")
    private LocalDate monitoringDate;

}
