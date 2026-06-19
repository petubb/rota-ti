package com.rotati.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "perguntas")
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String categoria;

    @NotBlank
    @Size(max = 80)
    @Column(name = "area_slug", length = 80, nullable = false)
    private String areaSlug;

    public Pergunta() {
    }

    public Pergunta(String texto, String categoria, String areaSlug) {
        this.texto = texto;
        this.categoria = categoria;
        this.areaSlug = areaSlug;
    }

    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAreaSlug() {
        return areaSlug;
    }

    public void setAreaSlug(String areaSlug) {
        this.areaSlug = areaSlug;
    }
}
