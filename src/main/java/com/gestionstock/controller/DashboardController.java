package com.gestionstock.controller;

import com.gestionstock.repository.StockAlertRepository;
import com.gestionstock.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final DashboardService dashboardService;
    private final StockAlertRepository alertRepository;

    public DashboardController(DashboardService dashboardService, StockAlertRepository alertRepository) {
        this.dashboardService = dashboardService;
        this.alertRepository = alertRepository;
    }

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("stats", dashboardService.stats());
        model.addAttribute("movements", dashboardService.recentMovements());
        model.addAttribute("alerts", alertRepository.findByResolvedFalseOrderByCreatedAtDesc());
        return "dashboard";
    }
}
