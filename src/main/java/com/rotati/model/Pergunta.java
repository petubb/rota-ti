package com.rotati.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "perguntas")
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false, unique = true)
    private String codigo;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TipoPergunta tipo;

    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private List<PerguntaPeso> pesos = new ArrayList<>();

    public Pergunta() {
    }

    public Pergunta(String codigo, String texto, String categoria, AreaTi areaPrincipal, TipoPergunta tipo) {
        this.codigo = codigo;
        this.texto = texto;
        this.categoria = categoria;
        this.areaSlug = areaPrincipal.getSlug();
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
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

    public TipoPergunta getTipo() {
        return tipo;
    }

    public List<PerguntaPeso> getPesos() {
        return List.copyOf(pesos);
    }

    public Pergunta adicionarPeso(AreaTi area, int peso) {
        pesos.add(new PerguntaPeso(this, area, peso));
        return this;
    }

    public int getPeso(AreaTi area) {
        return pesos.stream()
                .filter(item -> item.getAreaSlug().equals(area.getSlug()))
                .mapToInt(PerguntaPeso::getPeso)
                .findFirst()
                .orElse(0);
    }
}
