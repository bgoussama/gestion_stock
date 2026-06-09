package com.gestionstock.controller;

import com.gestionstock.repository.StockAlertRepository;
import com.gestionstock.service.AlertService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alerts")
public class AlertController {
    private final StockAlertRepository alertRepository;
    private final AlertService alertService;

    public AlertController(StockAlertRepository alertRepository, AlertService alertService) {
        this.alertRepository = alertRepository;
        this.alertService = alertService;
    }

    @GetMapping
    String list(Model model) {
        model.addAttribute("alerts", alertRepository.findAllByOrderByCreatedAtDesc());
        return "alerts/list";
    }

    @PostMapping("/{id}/resolve")
    String resolve(@PathVariable Long id) {
        alertService.resolve(id);
        return "redirect:/alerts";
    }
}
