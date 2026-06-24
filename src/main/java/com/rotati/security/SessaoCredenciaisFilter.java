package com.rotati.security;

import com.rotati.repository.ContaRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SessaoCredenciaisFilter extends OncePerRequestFilter {

    private final ContaRepository contaRepository;

    public SessaoCredenciaisFilter(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof ContaPrincipal principal) {
            boolean credenciaisValidas = contaRepository.findById(principal.getId())
                    .map(conta -> conta.isAtivo()
                            && conta.getVersaoCredenciais() == principal.getVersaoCredenciais())
                    .orElse(false);
            if (!credenciaisValidas) {
                SecurityContextHolder.clearContext();
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
