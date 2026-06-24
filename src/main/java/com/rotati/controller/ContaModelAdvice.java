package com.rotati.controller;

import com.rotati.security.ContaPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Objects;

@ControllerAdvice
public class ContaModelAdvice {

    @ModelAttribute("contaLogada")
    public ContaPrincipal contaLogada() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof ContaPrincipal principal) {
            return principal;
        }
        return null;
    }

    @ModelAttribute("contaAdmin")
    public boolean contaAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .anyMatch(authority -> Objects.equals(authority, "ROLE_ADMIN"));
    }
}
