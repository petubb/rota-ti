package com.rotati.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "pergunta_pesos",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_pergunta_pesos_pergunta_area",
                columnNames = {"pergunta_id", "area_slug"}
        )
)
public class PerguntaPeso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pergunta_id", nullable = false)
    private Pergunta pergunta;

    @NotBlank
    @Size(max = 80)
    @Column(name = "area_slug", length = 80, nullable = false)
    private String areaSlug;

    @Min(-2)
    @Max(2)
    @Column(nullable = false)
    private int peso;

    public PerguntaPeso() {
    }

    public PerguntaPeso(Pergunta pergunta, AreaTi area, int peso) {
        if (peso == 0) {
            throw new IllegalArgumentException("O peso de uma area nao pode ser zero.");
        }
        this.pergunta = pergunta;
        this.areaSlug = area.getSlug();
        this.peso = peso;
    }

    public Long getId() {
        return id;
    }

    public String getAreaSlug() {
        return areaSlug;
    }

    public int getPeso() {
        return peso;
    }
}
