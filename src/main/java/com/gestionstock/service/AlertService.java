package com.gestionstock.service;

import com.gestionstock.model.Product;
import com.gestionstock.model.StockAlert;
import com.gestionstock.repository.StockAlertRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertService {
    private final StockAlertRepository alertRepository;

    public AlertService(StockAlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Transactional
    public void synchronize(Product product) {
        var existing = alertRepository.findByProductIdAndResolvedFalse(product.getId());
        if (product.isLowStock()) {
            existing.orElseGet(() -> createAlert(product));
            return;
        }
        existing.ifPresent(this::resolve);
    }

    @Transactional
    public void resolve(Long id) {
        alertRepository.findById(id).ifPresent(this::resolve);
    }

    private StockAlert createAlert(Product product) {
        StockAlert alert = new StockAlert();
        alert.setProduct(product);
        alert.setMessage("Stock bas pour " + product.getName() + " (" + product.getQuantity() + " restant)");
        return alertRepository.save(alert);
    }

    private void resolve(StockAlert alert) {
        alert.setResolved(true);
        alert.setResolvedAt(LocalDateTime.now());
        alertRepository.save(alert);
    }
}
