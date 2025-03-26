package com.example.ecomadmin.store.reposiroty;

import com.example.ecomadmin.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s " +
            "WHERE (:totalRating IS NULL OR s.totalRating = :totalRating) " +
            "AND (:storeStatus IS NULL OR s.storeStatus = :storeStatus) " +
            "ORDER BY s.monitoringDate DESC")
    List<Store> findAllByConditions(
            @Param("totalRating") Integer totalRating,
            @Param("storeStatus") String storeStatus,
            Pageable pageable
    );

    @Query("SELECT s FROM Store s " +
            "WHERE (:totalRating IS NULL OR s.totalRating = :totalRating)" +
            "AND (:storeStatus IS NULL OR s.storeStatus = :storeStatus)" +
            "ORDER BY s.monitoringDate DESC")
    Page<Store> findAllByConditionsWithPaging(
            @Param("totalRating") Integer totalRating,
            @Param("storeStatus") String storeStatus,
            Pageable pageable
    );
}
