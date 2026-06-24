package com.rotati.controller;

import com.rotati.dto.CadastroForm;
import com.rotati.model.Conta;
import com.rotati.security.ContaPrincipal;
import com.rotati.service.CadastroException;
import com.rotati.service.ContaService;
import com.rotati.service.ResultadoContaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final ContaService contaService;
    private final ResultadoContaService resultadoContaService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public AuthController(
            ContaService contaService,
            ResultadoContaService resultadoContaService,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository
    ) {
        this.contaService = contaService;
        this.resultadoContaService = resultadoContaService;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/entrar")
    public String entrar(@AuthenticationPrincipal ContaPrincipal principal) {
        return principal == null ? "auth/entrar" : "redirect:/minha-conta/resultados";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model, @AuthenticationPrincipal ContaPrincipal principal) {
        if (principal != null) {
            return "redirect:/minha-conta/resultados";
        }
        if (!model.containsAttribute("cadastroForm")) {
            model.addAttribute("cadastroForm", new CadastroForm());
        }
        return "auth/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @Valid @ModelAttribute("cadastroForm") CadastroForm form,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "auth/cadastro";
        }

        Conta conta;
        try {
            conta = contaService.criarConta(form);
        } catch (CadastroException exception) {
            bindingResult.rejectValue(exception.getCampo(), "cadastro.invalido", exception.getMessage());
            return "auth/cadastro";
        }

        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(conta.getEmail(), form.getSenha())
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        request.changeSessionId();
        securityContextRepository.saveContext(context, request, response);

        ContaPrincipal principal = (ContaPrincipal) authentication.getPrincipal();
        Long resultadoVinculado = resultadoContaService.vincularResultadoPendente(principal, session);
        redirectAttributes.addFlashAttribute("mensagem", "Conta criada com seguranca.");

        return resultadoVinculado == null
                ? "redirect:/minha-conta/resultados"
                : "redirect:/resultado/" + resultadoVinculado;
    }
}
