package com.rotati.controller;

import com.rotati.model.AreaTi;
import com.rotati.service.AreaService;
import com.rotati.service.ConteudoAreaService;
import com.rotati.service.DetalheAreaService;
import com.rotati.service.ExploracaoAreaService;
import com.rotati.service.RoadmapConteudoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AreaController {

    private final AreaService areaService;
    private final ConteudoAreaService conteudoAreaService;
    private final DetalheAreaService detalheAreaService;
    private final RoadmapConteudoService roadmapConteudoService;
    private final ExploracaoAreaService exploracaoAreaService;

    public AreaController(
            AreaService areaService,
            ConteudoAreaService conteudoAreaService,
            DetalheAreaService detalheAreaService,
            RoadmapConteudoService roadmapConteudoService,
            ExploracaoAreaService exploracaoAreaService
    ) {
        this.areaService = areaService;
        this.conteudoAreaService = conteudoAreaService;
        this.detalheAreaService = detalheAreaService;
        this.roadmapConteudoService = roadmapConteudoService;
        this.exploracaoAreaService = exploracaoAreaService;
    }

    @GetMapping("/areas")
    public String listar(Model model) {
        model.addAttribute("areas", exploracaoAreaService.listar());
        return "areas/list";
    }

    @GetMapping("/area/{slug}")
    public String detalhe(@PathVariable String slug, Model model) {
        AreaTi area = areaService.buscarPorSlug(slug);
        model.addAttribute("area", area);
        model.addAttribute("detalheArea", detalheAreaService.buscarPorArea(area));
        model.addAttribute("conteudoArea", conteudoAreaService.buscarPorSlug(area.getSlug()));
        model.addAttribute("destaquesRondonia", conteudoAreaService.listarDestaquesRondonia());
        model.addAttribute("exploracaoArea", exploracaoAreaService.buscarPorArea(area));
        return "areas/detail";
    }

    @GetMapping("/area/{slug}/roadmap")
    public String roadmap(@PathVariable String slug, Model model) {
        AreaTi area = areaService.buscarPorSlug(slug);
        model.addAttribute("area", area);
        model.addAttribute("detalheArea", detalheAreaService.buscarPorArea(area));
        model.addAttribute("roadmap", roadmapConteudoService.buscarPorArea(area));
        return "areas/roadmap";
    }
}
