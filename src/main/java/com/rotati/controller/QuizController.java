package com.rotati.controller;

import com.rotati.dto.QuizSubmission;
import com.rotati.dto.ResultadoView;
import com.rotati.model.Pergunta;
import com.rotati.model.Resultado;
import com.rotati.service.ConteudoAreaService;
import com.rotati.service.QuizService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QuizController {

    private static final String QUIZ_PENDENTE = "quizPendente";
    private static final String IDS_DESEMPATE = "idsDesempate";

    private final QuizService quizService;
    private final ConteudoAreaService conteudoAreaService;

    public QuizController(QuizService quizService, ConteudoAreaService conteudoAreaService) {
        this.quizService = quizService;
        this.conteudoAreaService = conteudoAreaService;
    }

    @GetMapping("/quiz")
    public String quiz(Model model, HttpSession session) {
        limparQuizPendente(session);
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
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        if (!bindingResult.hasErrors() && !quizService.todasPerguntasRespondidas(submission)) {
            bindingResult.reject("respostas.obrigatorias", "Responda todas as perguntas antes de ver o resultado.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("perguntas", quizService.listarPerguntas());
            return "quiz";
        }

        List<Pergunta> perguntasDesempate = quizService.selecionarPerguntasDesempate(submission);
        if (!perguntasDesempate.isEmpty()) {
            session.setAttribute(QUIZ_PENDENTE, copiarSubmission(submission));
            session.setAttribute(IDS_DESEMPATE, perguntasDesempate.stream().map(Pergunta::getId).toList());
            model.addAttribute("submission", new QuizSubmission());
            model.addAttribute("perguntas", perguntasDesempate);
            return "quiz-desempate";
        }

        Resultado resultado = quizService.processar(submission);
        redirectAttributes.addFlashAttribute("mensagem", "Resultado gerado com sucesso.");
        return "redirect:/resultado/" + resultado.getId();
    }

    @PostMapping("/quiz/desempate")
    public String processarDesempate(
            @ModelAttribute("submission") QuizSubmission submission,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        QuizSubmission quizPendente = (QuizSubmission) session.getAttribute(QUIZ_PENDENTE);
        @SuppressWarnings("unchecked")
        List<Long> idsDesempate = (List<Long>) session.getAttribute(IDS_DESEMPATE);

        if (quizPendente == null || idsDesempate == null) {
            redirectAttributes.addFlashAttribute("mensagem", "O teste expirou. Responda novamente para gerar seu resultado.");
            return "redirect:/quiz";
        }

        List<Pergunta> perguntasDesempate = quizService.buscarPerguntasPorIds(idsDesempate);
        if (!quizService.perguntasRespondidas(perguntasDesempate, submission)) {
            model.addAttribute("perguntas", perguntasDesempate);
            model.addAttribute("erroDesempate", "Responda as duas perguntas para concluir seu resultado.");
            return "quiz-desempate";
        }

        Map<Long, Integer> respostasCompletas = new HashMap<>(quizPendente.getRespostas());
        idsDesempate.forEach(id -> respostasCompletas.put(id, submission.getRespostas().get(id)));
        quizPendente.setRespostas(respostasCompletas);

        Resultado resultado = quizService.processar(quizPendente);
        limparQuizPendente(session);
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

    private QuizSubmission copiarSubmission(QuizSubmission original) {
        QuizSubmission copia = new QuizSubmission();
        copia.setIdade(original.getIdade());
        copia.setEscola(original.getEscola());
        copia.setRespostas(new HashMap<>(original.getRespostas()));
        return copia;
    }

    private void limparQuizPendente(HttpSession session) {
        session.removeAttribute(QUIZ_PENDENTE);
        session.removeAttribute(IDS_DESEMPATE);
    }
}
