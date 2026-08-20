package com.rotati.controller;

import com.rotati.dto.DashboardFiltros;
import com.rotati.service.MetricaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

    private final MetricaService metricaService;

    public DashboardController(MetricaService metricaService) {
        this.metricaService = metricaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(defaultValue = "7") int diasAtividade,
            @RequestParam(defaultValue = "30") int diasResultados,
            @RequestParam(defaultValue = "30") int diasContas,
            Model model
    ) {
        DashboardFiltros filtros = DashboardFiltros.de(diasAtividade, diasResultados, diasContas);
        model.addAttribute("filtros", filtros);
        model.addAttribute("metricas", metricaService.gerarDashboard(filtros));
        return "dashboard";
    }
}
