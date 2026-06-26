package com.rotati.dto;

import java.util.List;

public class DetalheAreaView {

    private final SalarioAreaView salario;
    private final String mercado;
    private final List<PlanoCarreiraView> planoCarreira;
    private final List<ReferenciaAreaView> referencias;
    private final List<String> ferramentas;

    public DetalheAreaView(
            SalarioAreaView salario,
            String mercado,
            List<PlanoCarreiraView> planoCarreira,
            List<ReferenciaAreaView> referencias,
            List<String> ferramentas
    ) {
        this.salario = salario;
        this.mercado = mercado;
        this.planoCarreira = List.copyOf(planoCarreira);
        this.referencias = List.copyOf(referencias);
        this.ferramentas = List.copyOf(ferramentas);
    }

    public SalarioAreaView getSalario() {
        return salario;
    }

    public String getMercado() {
        return mercado;
    }

    public List<PlanoCarreiraView> getPlanoCarreira() {
        return planoCarreira;
    }

    public List<ReferenciaAreaView> getReferencias() {
        return referencias;
    }

    public List<String> getFerramentas() {
        return ferramentas;
    }
}
