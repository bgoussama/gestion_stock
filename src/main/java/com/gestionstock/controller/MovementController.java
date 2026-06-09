package com.gestionstock.controller;

import com.gestionstock.model.MovementType;
import com.gestionstock.model.StockMovement;
import com.gestionstock.repository.ProductRepository;
import com.gestionstock.repository.StockMovementRepository;
import com.gestionstock.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movements")
public class MovementController {
    private final StockMovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final StockMovementService movementService;

    public MovementController(StockMovementRepository movementRepository, ProductRepository productRepository,
            StockMovementService movementService) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
        this.movementService = movementService;
    }

    @GetMapping
    String list(Model model) {
        model.addAttribute("movements", movementRepository.findTop20ByOrderByCreatedAtDesc());
        model.addAttribute("movement", new StockMovement());
        addFormData(model);
        return "movements/list";
    }

    @PostMapping
    String save(@Valid @ModelAttribute("movement") StockMovement movement, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("movements", movementRepository.findTop20ByOrderByCreatedAtDesc());
            addFormData(model);
            return "movements/list";
        }
        try {
            movementService.register(movement);
        } catch (IllegalArgumentException exception) {
            result.reject("stock", exception.getMessage());
            model.addAttribute("movements", movementRepository.findTop20ByOrderByCreatedAtDesc());
            addFormData(model);
            return "movements/list";
        }
        return "redirect:/movements";
    }

    private void addFormData(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("types", MovementType.values());
    }
}
