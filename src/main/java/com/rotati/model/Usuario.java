package com.rotati.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 80)
    @Column(length = 80, nullable = false)
    private String nome;

    @NotNull
    @Min(12)
    @Max(25)
    private Integer idade;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false)
    private String escola;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Usuario() {
    }

    public Usuario(Integer idade, String escola) {
        this("Estudante", idade, escola);
    }

    public Usuario(String nome, Integer idade, String escola) {
        this.nome = nome;
        this.idade = idade;
        this.escola = escola;
    }

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
