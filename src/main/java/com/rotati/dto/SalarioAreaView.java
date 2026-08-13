package com.rotati.dto;

import java.util.List;

public class SalarioAreaView {

    private final String resumo;
    private final String cargoReferencia;
    private final String escopo;
    private final String competencia;
    private final String natureza;
    private final String observacao;
    private final String metodologia;
    private final List<FaixaSalarioView> faixas;
    private final List<FonteSalarioView> fontes;

    public SalarioAreaView(
            String resumo,
            String cargoReferencia,
            String escopo,
            String competencia,
            String natureza,
            String observacao,
            String metodologia,
            List<FaixaSalarioView> faixas,
            List<FonteSalarioView> fontes
    ) {
        this.resumo = resumo;
        this.cargoReferencia = cargoReferencia;
        this.escopo = escopo;
        this.competencia = competencia;
        this.natureza = natureza;
        this.observacao = observacao;
        this.metodologia = metodologia;
        this.faixas = List.copyOf(faixas);
        this.fontes = List.copyOf(fontes);
    }

    public String getResumo() {
        return resumo;
    }

    public String getCargoReferencia() {
        return cargoReferencia;
    }

    public String getEscopo() {
        return escopo;
    }

    public String getCompetencia() {
        return competencia;
    }

    public String getNatureza() {
        return natureza;
    }

    public String getObservacao() {
        return observacao;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public List<FaixaSalarioView> getFaixas() {
        return faixas;
    }

    public List<FonteSalarioView> getFontes() {
        return fontes;
    }
}
