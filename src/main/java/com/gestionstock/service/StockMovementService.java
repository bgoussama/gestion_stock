package com.gestionstock.service;

import com.gestionstock.model.MovementType;
import com.gestionstock.model.StockMovement;
import com.gestionstock.repository.ProductRepository;
import com.gestionstock.repository.StockMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockMovementService {
    private final ProductRepository productRepository;
    private final StockMovementRepository movementRepository;
    private final AlertService alertService;

    public StockMovementService(ProductRepository productRepository, StockMovementRepository movementRepository,
            AlertService alertService) {
        this.productRepository = productRepository;
        this.movementRepository = movementRepository;
        this.alertService = alertService;
    }

    @Transactional
    public StockMovement register(StockMovement movement) {
        var product = productRepository.findById(movement.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));
        int newQuantity = product.getQuantity();
        if (movement.getType() == MovementType.SORTIE || movement.getType() == MovementType.LIVRAISON) {
            newQuantity -= movement.getQuantity();
        } else {
            newQuantity += movement.getQuantity();
        }
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Stock insuffisant pour ce mouvement");
        }
        product.setQuantity(newQuantity);
        movement.setProduct(product);
        var saved = movementRepository.save(movement);
        productRepository.save(product);
        alertService.synchronize(product);
        return saved;
    }
}
