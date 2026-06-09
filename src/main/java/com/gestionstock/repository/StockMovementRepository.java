package com.gestionstock.repository;

import com.gestionstock.model.StockMovement;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    @EntityGraph(attributePaths = "product")
    List<StockMovement> findTop20ByOrderByCreatedAtDesc();

    void deleteByProduct_Id(Long productId);
}
