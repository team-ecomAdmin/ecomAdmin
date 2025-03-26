package com.example.ecomadmin.store.repository;

import com.example.ecomadmin.store.entity.Stores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Stores, Long> {
}
