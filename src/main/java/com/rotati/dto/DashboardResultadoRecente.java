package com.rotati.dto;

public class DashboardResultadoRecente {

    private final Long id;
    private final String areaTitulo;
    private final String areaSlug;
    private final double score;
    private final Integer satisfacao;
    private final Integer idade;
    private final String escola;
    private final String contaNome;
    private final String criadoEm;

    public DashboardResultadoRecente(
            Long id,
            String areaTitulo,
            String areaSlug,
            double score,
            Integer satisfacao,
            Integer idade,
            String escola,
            String contaNome,
            String criadoEm
    ) {
        this.id = id;
        this.areaTitulo = areaTitulo;
        this.areaSlug = areaSlug;
        this.score = score;
        this.satisfacao = satisfacao;
        this.idade = idade;
        this.escola = escola;
        this.contaNome = contaNome;
        this.criadoEm = criadoEm;
    }

    public Long getId() {
        return id;
    }

    public String getAreaTitulo() {
        return areaTitulo;
    }

    public String getAreaSlug() {
        return areaSlug;
    }

    public double getScore() {
        return score;
    }

    public Integer getSatisfacao() {
        return satisfacao;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getEscola() {
        return escola;
    }

    public String getContaNome() {
        return contaNome;
    }

    public String getCriadoEm() {
        return criadoEm;
    }
}
