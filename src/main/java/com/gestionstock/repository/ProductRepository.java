package com.gestionstock.repository;

import com.gestionstock.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = {"category", "supplier"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"category", "supplier"})
    List<Product> findByNameContainingIgnoreCaseOrReferenceContainingIgnoreCase(String name, String reference);

    long countByQuantityLessThanEqual(int threshold);
}
