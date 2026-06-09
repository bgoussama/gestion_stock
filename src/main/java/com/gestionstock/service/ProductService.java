package com.gestionstock.service;

import com.gestionstock.repository.ProductRepository;
import com.gestionstock.repository.StockAlertRepository;
import com.gestionstock.repository.StockMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StockAlertRepository alertRepository;
    private final StockMovementRepository movementRepository;

    public ProductService(ProductRepository productRepository, StockAlertRepository alertRepository,
            StockMovementRepository movementRepository) {
        this.productRepository = productRepository;
        this.alertRepository = alertRepository;
        this.movementRepository = movementRepository;
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            return;
        }
        alertRepository.deleteByProduct_Id(id);
        movementRepository.deleteByProduct_Id(id);
        productRepository.deleteById(id);
    }
}
