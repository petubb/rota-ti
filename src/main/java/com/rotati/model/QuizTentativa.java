package com.rotati.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tentativas_quiz")
public class QuizTentativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resultado_id", unique = true)
    private Resultado resultado;

    @Column(name = "iniciada_em", nullable = false)
    private LocalDateTime iniciadaEm;

    @Column(name = "concluida_em")
    private LocalDateTime concluidaEm;

    public QuizTentativa() {
    }

    public QuizTentativa(LocalDateTime iniciadaEm) {
        this.iniciadaEm = iniciadaEm;
    }

    @PrePersist
    void prePersist() {
        if (iniciadaEm == null) {
            iniciadaEm = LocalDateTime.now();
        }
    }

    public void concluir(Resultado resultado, LocalDateTime momento) {
        this.resultado = resultado;
        this.concluidaEm = momento;
    }

    public boolean estaConcluida() {
        return concluidaEm != null;
    }

    public Long getId() {
        return id;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public LocalDateTime getIniciadaEm() {
        return iniciadaEm;
    }

    public LocalDateTime getConcluidaEm() {
        return concluidaEm;
    }
}
