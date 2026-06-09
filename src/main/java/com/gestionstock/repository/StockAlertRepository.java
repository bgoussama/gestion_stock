package com.gestionstock.repository;

import com.gestionstock.model.StockAlert;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockAlertRepository extends JpaRepository<StockAlert, Long> {
    @EntityGraph(attributePaths = "product")
    List<StockAlert> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = "product")
    List<StockAlert> findByResolvedFalseOrderByCreatedAtDesc();

    Optional<StockAlert> findByProductIdAndResolvedFalse(Long productId);

    void deleteByProduct_Id(Long productId);
}
