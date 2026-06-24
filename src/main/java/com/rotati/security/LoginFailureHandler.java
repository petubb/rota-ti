package com.rotati.security;

import com.rotati.service.ContaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final ContaService contaService;

    public LoginFailureHandler(ContaService contaService) {
        this.contaService = contaService;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        contaService.registrarFalhaLogin(request.getParameter("email"));
        response.sendRedirect(request.getContextPath() + "/entrar?erro");
    }
}
