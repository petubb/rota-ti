package com.rotati.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "tokens_recuperacao_senha",
        uniqueConstraints = @UniqueConstraint(name = "uk_tokens_recuperacao_hash", columnNames = "token_hash")
)
public class TokenRecuperacaoSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @Column(name = "token_hash", length = 64, nullable = false)
    private String tokenHash;

    @Column(name = "expira_em", nullable = false)
    private LocalDateTime expiraEm;

    @Column(name = "usado_em")
    private LocalDateTime usadoEm;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public TokenRecuperacaoSenha() {
    }

    public TokenRecuperacaoSenha(Conta conta, String tokenHash, LocalDateTime expiraEm) {
        this.conta = conta;
        this.tokenHash = tokenHash;
        this.expiraEm = expiraEm;
    }

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public boolean estaValido(LocalDateTime agora) {
        return usadoEm == null && expiraEm.isAfter(agora);
    }

    public void marcarComoUsado(LocalDateTime agora) {
        usadoEm = agora;
    }

    public Long getId() {
        return id;
    }

    public Conta getConta() {
        return conta;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public LocalDateTime getExpiraEm() {
        return expiraEm;
    }

    public LocalDateTime getUsadoEm() {
        return usadoEm;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
