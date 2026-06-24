package com.rotati.security;

import com.rotati.service.ContaService;
import com.rotati.service.ResultadoContaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ContaService contaService;
    private final ResultadoContaService resultadoContaService;

    public LoginSuccessHandler(ContaService contaService, ResultadoContaService resultadoContaService) {
        this.contaService = contaService;
        this.resultadoContaService = resultadoContaService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        contaService.registrarLoginBemSucedido(authentication.getName());
        ContaPrincipal principal = (ContaPrincipal) authentication.getPrincipal();
        Long resultadoVinculado = resultadoContaService.vincularResultadoPendente(principal, request.getSession());
        response.sendRedirect(request.getContextPath() + (resultadoVinculado == null
                ? "/minha-conta/resultados"
                : "/resultado/" + resultadoVinculado));
    }
}
