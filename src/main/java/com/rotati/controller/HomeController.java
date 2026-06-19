package com.rotati.controller;

import com.rotati.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AreaService areaService;

    public HomeController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("areas", areaService.listarAreas());
        return "index";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }
}
