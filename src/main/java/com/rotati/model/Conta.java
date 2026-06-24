package com.rotati.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "contas",
        uniqueConstraints = @UniqueConstraint(name = "uk_contas_email", columnNames = "email")
)
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false)
    private String nome;

    @Column(length = 150, nullable = false)
    private String email;

    @Column(name = "senha_hash", length = 100, nullable = false)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PapelConta papel = PapelConta.USER;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "tentativas_falhas", nullable = false)
    private int tentativasFalhas;

    @Column(name = "bloqueado_ate")
    private LocalDateTime bloqueadoAte;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Conta() {
    }

    public Conta(String nome, String email, String senhaHash) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
    }

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public void registrarFalhaLogin(int limiteTentativas, int minutosBloqueio) {
        if (estaBloqueada()) {
            return;
        }

        tentativasFalhas++;
        if (tentativasFalhas >= limiteTentativas) {
            bloqueadoAte = LocalDateTime.now().plusMinutes(minutosBloqueio);
        }
    }

    public void registrarLoginBemSucedido() {
        tentativasFalhas = 0;
        bloqueadoAte = null;
    }

    public boolean estaBloqueada() {
        return bloqueadoAte != null && bloqueadoAte.isAfter(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public PapelConta getPapel() {
        return papel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getTentativasFalhas() {
        return tentativasFalhas;
    }

    public LocalDateTime getBloqueadoAte() {
        return bloqueadoAte;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
