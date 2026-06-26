package com.rotati.controller;

import com.rotati.model.AreaTi;
import com.rotati.service.AreaService;
import com.rotati.service.ConteudoAreaService;
import com.rotati.service.DetalheAreaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AreaController {

    private final AreaService areaService;
    private final ConteudoAreaService conteudoAreaService;
    private final DetalheAreaService detalheAreaService;

    public AreaController(
            AreaService areaService,
            ConteudoAreaService conteudoAreaService,
            DetalheAreaService detalheAreaService
    ) {
        this.areaService = areaService;
        this.conteudoAreaService = conteudoAreaService;
        this.detalheAreaService = detalheAreaService;
    }

    @GetMapping("/areas")
    public String listar(Model model) {
        model.addAttribute("areas", areaService.listarAreas());
        return "areas/list";
    }

    @GetMapping("/area/{slug}")
    public String detalhe(@PathVariable String slug, Model model) {
        AreaTi area = areaService.buscarPorSlug(slug);
        model.addAttribute("area", area);
        model.addAttribute("detalheArea", detalheAreaService.buscarPorArea(area));
        model.addAttribute("conteudoArea", conteudoAreaService.buscarPorSlug(area.getSlug()));
        model.addAttribute("destaquesRondonia", conteudoAreaService.listarDestaquesRondonia());
        return "areas/detail";
    }
}
