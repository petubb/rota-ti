package com.rotati.controller;

import com.rotati.model.AreaTi;
import com.rotati.model.Pergunta;
import com.rotati.service.AreaService;
import com.rotati.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final AreaService areaService;
    private final QuizService quizService;

    public ApiController(AreaService areaService, QuizService quizService) {
        this.areaService = areaService;
        this.quizService = quizService;
    }

    @GetMapping("/areas")
    public List<AreaTi> areas() {
        return areaService.listarAreas();
    }

    @GetMapping("/perguntas")
    public List<Pergunta> perguntas() {
        return quizService.listarPerguntas();
    }
}
