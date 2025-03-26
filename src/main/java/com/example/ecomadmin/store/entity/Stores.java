package com.example.ecomadmin.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stores")
@Getter
@Builder
public class Stores {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String companyName;
    @Column
    private String domainName;
    @Column
    private String email;
    @Column
    private String storeStatus;
    @Column
    private int totalRating;
    @Column
    private LocalDateTime monitoringDate;

}
