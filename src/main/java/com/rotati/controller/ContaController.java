package com.rotati.controller;

import com.rotati.security.ContaPrincipal;
import com.rotati.service.ResultadoContaService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContaController {

    private final ResultadoContaService resultadoContaService;

    public ContaController(ResultadoContaService resultadoContaService) {
        this.resultadoContaService = resultadoContaService;
    }

    @GetMapping("/minha-conta/resultados")
    public String resultados(@AuthenticationPrincipal ContaPrincipal principal, Model model) {
        model.addAttribute("resultados", resultadoContaService.listarHistorico(principal));
        return "conta/resultados";
    }
}
