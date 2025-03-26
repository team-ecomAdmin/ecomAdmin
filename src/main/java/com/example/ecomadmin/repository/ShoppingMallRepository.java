package com.example.ecomadmin.repository;

import com.example.ecomadmin.entity.Stores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingMallRepository extends JpaRepository<Stores, Long> {
}
