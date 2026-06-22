package com.rotati.controller;

import com.rotati.dto.QuizSubmission;
import com.rotati.dto.ResultadoView;
import com.rotati.model.Resultado;
import com.rotati.service.ConteudoAreaService;
import com.rotati.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuizController {

    private final QuizService quizService;
    private final ConteudoAreaService conteudoAreaService;

    public QuizController(QuizService quizService, ConteudoAreaService conteudoAreaService) {
        this.quizService = quizService;
        this.conteudoAreaService = conteudoAreaService;
    }

    @GetMapping("/quiz")
    public String quiz(Model model) {
        if (!model.containsAttribute("submission")) {
            model.addAttribute("submission", new QuizSubmission());
        }
        model.addAttribute("perguntas", quizService.listarPerguntas());
        return "quiz";
    }

    @PostMapping("/quiz")
    public String processar(
            @Valid @ModelAttribute("submission") QuizSubmission submission,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (!bindingResult.hasErrors() && !quizService.todasPerguntasRespondidas(submission)) {
            bindingResult.reject("respostas.obrigatorias", "Responda todas as perguntas antes de ver o resultado.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("perguntas", quizService.listarPerguntas());
            return "quiz";
        }

        Resultado resultado = quizService.processar(submission);
        redirectAttributes.addFlashAttribute("mensagem", "Resultado gerado com sucesso.");
        return "redirect:/resultado/" + resultado.getId();
    }

    @GetMapping("/resultado")
    public String resultadoSemId() {
        return "redirect:/quiz";
    }

    @GetMapping("/resultado/{id}")
    public String resultado(@PathVariable Long id, Model model) {
        ResultadoView resultadoView = quizService.buscarResultado(id);
        String areaSlug = resultadoView.getPrincipal().getArea().getSlug();

        model.addAttribute("resultadoView", resultadoView);
        model.addAttribute("conteudoArea", conteudoAreaService.buscarPorSlug(areaSlug));
        model.addAttribute("destaquesRondonia", conteudoAreaService.listarDestaquesRondonia());
        return "resultado";
    }

    @PostMapping("/resultado/{id}/satisfacao")
    public String satisfacao(@PathVariable Long id, Integer satisfacao, RedirectAttributes redirectAttributes) {
        quizService.registrarSatisfacao(id, satisfacao);
        redirectAttributes.addFlashAttribute("mensagem", "Obrigado pelo feedback!");
        return "redirect:/resultado/" + id;
    }
}
