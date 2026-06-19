package com.rotati.controller;

import com.rotati.service.AreaService;
import com.rotati.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AreaService areaService;
    private final QuizService quizService;

    public HomeController(AreaService areaService, QuizService quizService) {
        this.areaService = areaService;
        this.quizService = quizService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("areas", areaService.listarAreas());
        model.addAttribute("totalPerguntas", quizService.totalPerguntas());
        return "index";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }
}
