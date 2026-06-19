package com.rotati.controller;

import com.rotati.service.MetricaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final MetricaService metricaService;

    public DashboardController(MetricaService metricaService) {
        this.metricaService = metricaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("metricas", metricaService.gerarDashboard());
        return "dashboard";
    }
}
