package com.rotati.controller;

import com.rotati.dto.RedefinicaoSenhaForm;
import com.rotati.dto.SolicitacaoRecuperacaoForm;
import com.rotati.service.CadastroException;
import com.rotati.service.EmailRecuperacaoSenhaService;
import com.rotati.service.RecuperacaoSenhaService;
import com.rotati.service.TokenRecuperacaoException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RecuperacaoSenhaController {

    private final RecuperacaoSenhaService recuperacaoSenhaService;
    private final EmailRecuperacaoSenhaService emailService;

    public RecuperacaoSenhaController(
            RecuperacaoSenhaService recuperacaoSenhaService,
            EmailRecuperacaoSenhaService emailService
    ) {
        this.recuperacaoSenhaService = recuperacaoSenhaService;
        this.emailService = emailService;
    }

    @GetMapping("/esqueci-senha")
    public String solicitar(Model model, HttpServletResponse response) {
        impedirCache(response);
        if (!model.containsAttribute("solicitacaoForm")) {
            model.addAttribute("solicitacaoForm", new SolicitacaoRecuperacaoForm());
        }
        model.addAttribute("emailConfigurado", emailService.estaConfigurado());
        return "auth/esqueci-senha";
    }

    @PostMapping("/esqueci-senha")
    public String solicitar(
            @Valid @ModelAttribute("solicitacaoForm") SolicitacaoRecuperacaoForm form,
            BindingResult bindingResult,
            Model model,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        impedirCache(response);
        if (bindingResult.hasErrors()) {
            model.addAttribute("emailConfigurado", emailService.estaConfigurado());
            return "auth/esqueci-senha";
        }

        recuperacaoSenhaService.solicitar(form.getEmail());
        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Se existir uma conta com esse e-mail, enviaremos as instrucoes de recuperacao."
        );
        return "redirect:/esqueci-senha";
    }

    @GetMapping("/recuperar-senha")
    public String redefinir(
            @RequestParam(required = false) String token,
            Model model,
            HttpServletResponse response
    ) {
        impedirCache(response);
        if (!recuperacaoSenhaService.tokenValido(token)) {
            return "auth/token-invalido";
        }

        RedefinicaoSenhaForm form = new RedefinicaoSenhaForm();
        form.setToken(token);
        model.addAttribute("redefinicaoForm", form);
        return "auth/redefinir-senha";
    }

    @PostMapping("/recuperar-senha")
    public String redefinir(
            @Valid @ModelAttribute("redefinicaoForm") RedefinicaoSenhaForm form,
            BindingResult bindingResult,
            HttpSession session,
            HttpServletResponse response
    ) {
        impedirCache(response);
        if (bindingResult.hasErrors()) {
            return recuperacaoSenhaService.tokenValido(form.getToken())
                    ? "auth/redefinir-senha"
                    : "auth/token-invalido";
        }

        try {
            recuperacaoSenhaService.redefinir(
                    form.getToken(),
                    form.getSenha(),
                    form.getConfirmarSenha()
            );
        } catch (CadastroException exception) {
            bindingResult.rejectValue(exception.getCampo(), "senha.invalida", exception.getMessage());
            return "auth/redefinir-senha";
        } catch (TokenRecuperacaoException exception) {
            return "auth/token-invalido";
        }

        session.invalidate();
        return "redirect:/entrar?senha-alterada";
    }

    private void impedirCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, max-age=0");
        response.setHeader("Pragma", "no-cache");
    }
}
