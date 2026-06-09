package com.gestionstock.service;

import com.gestionstock.repository.CategoryRepository;
import com.gestionstock.repository.ProductRepository;
import com.gestionstock.repository.StockAlertRepository;
import com.gestionstock.repository.StockMovementRepository;
import com.gestionstock.repository.SupplierRepository;
import com.gestionstock.model.StockMovement;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final StockAlertRepository alertRepository;
    private final StockMovementRepository movementRepository;

    public DashboardService(ProductRepository productRepository, CategoryRepository categoryRepository,
            SupplierRepository supplierRepository, StockAlertRepository alertRepository,
            StockMovementRepository movementRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.alertRepository = alertRepository;
        this.movementRepository = movementRepository;
    }

    public DashboardStats stats() {
        var products = productRepository.findAll();
        BigDecimal stockValue = products.stream()
                .map(product -> product.getUnitPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new DashboardStats(
                products.size(),
                categoryRepository.count(),
                supplierRepository.count(),
                alertRepository.findByResolvedFalseOrderByCreatedAtDesc().size(),
                stockValue);
    }

    public List<StockMovement> recentMovements() {
        return movementRepository.findTop20ByOrderByCreatedAtDesc();
    }

    public record DashboardStats(long products, long categories, long suppliers, long alerts, BigDecimal stockValue) {
    }
}
