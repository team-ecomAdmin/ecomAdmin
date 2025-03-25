package com.example.ecomadmin.store.reposiroty;

import com.example.ecomadmin.store.entity.Store;
import com.example.ecomadmin.store.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query(value =
            "SELECT * " +
                    "FROM Stores " +
                    "WHERE (:totalRating IS NULL OR total_rating = :totalRating) " +
                    "AND (:storeStatus IS NULL OR store_status = :storeStatus) " +
                    "ORDER BY monitoring_date DESC " +
                    "LIMIT 10", nativeQuery = true)
    List<Store> filterByRatingAndStatus(
            @Param("totalRating") Integer totalRating,
            @Param("storeStatus") StoreStatus storeStatus
    );
}
