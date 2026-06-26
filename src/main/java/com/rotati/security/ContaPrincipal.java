package com.rotati.security;

import com.rotati.model.Conta;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

public class ContaPrincipal implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Long id;
    private final String nome;
    private final String email;
    private final String senhaHash;
    private final String papel;
    private final boolean ativo;
    private final boolean bloqueada;
    private final int versaoCredenciais;

    public ContaPrincipal(Conta conta) {
        this.id = conta.getId();
        this.nome = conta.getNome();
        this.email = conta.getEmail();
        this.senhaHash = conta.getSenhaHash();
        this.papel = conta.getPapel().name();
        this.ativo = conta.isAtivo();
        this.bloqueada = conta.estaBloqueada();
        this.versaoCredenciais = conta.getVersaoCredenciais();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getVersaoCredenciais() {
        return versaoCredenciais;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + papel));
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !bloqueada;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }
}
