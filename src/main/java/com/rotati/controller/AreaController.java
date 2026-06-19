package com.rotati.controller;

import com.rotati.service.AreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping("/areas")
    public String listar(Model model) {
        model.addAttribute("areas", areaService.listarAreas());
        return "areas/list";
    }

    @GetMapping("/area/{slug}")
    public String detalhe(@PathVariable String slug, Model model) {
        model.addAttribute("area", areaService.buscarPorSlug(slug));
        return "areas/detail";
    }
}
