package com.rotati.dto;

import java.util.List;

public class SalarioAreaView {

    private final String resumo;
    private final String observacao;
    private final List<FonteSalarioView> fontes;

    public SalarioAreaView(String resumo, String observacao, List<FonteSalarioView> fontes) {
        this.resumo = resumo;
        this.observacao = observacao;
        this.fontes = List.copyOf(fontes);
    }

    public String getResumo() {
        return resumo;
    }

    public String getObservacao() {
        return observacao;
    }

    public List<FonteSalarioView> getFontes() {
        return fontes;
    }
}
