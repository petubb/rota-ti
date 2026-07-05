package com.rotati.dto;

import java.util.List;

public class DetalheAreaView {

    private final SalarioAreaView salario;
    private final String mercado;
    private final List<PlanoCarreiraView> planoCarreira;
    private final List<ReferenciaAreaView> referencias;
    private final List<ProfissaoAreaView> profissoes;
    private final List<FormacaoLocalView> formacoes;
    private final List<String> ferramentas;

    public DetalheAreaView(
            SalarioAreaView salario,
            String mercado,
            List<PlanoCarreiraView> planoCarreira,
            List<ReferenciaAreaView> referencias,
            List<String> ferramentas
    ) {
        this(salario, mercado, planoCarreira, referencias, List.of(), List.of(), ferramentas);
    }

    public DetalheAreaView(
            SalarioAreaView salario,
            String mercado,
            List<PlanoCarreiraView> planoCarreira,
            List<ReferenciaAreaView> referencias,
            List<ProfissaoAreaView> profissoes,
            List<FormacaoLocalView> formacoes,
            List<String> ferramentas
    ) {
        this.salario = salario;
        this.mercado = mercado;
        this.planoCarreira = List.copyOf(planoCarreira);
        this.referencias = List.copyOf(referencias);
        this.profissoes = List.copyOf(profissoes);
        this.formacoes = List.copyOf(formacoes);
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

    public List<ProfissaoAreaView> getProfissoes() {
        return profissoes;
    }

    public List<FormacaoLocalView> getFormacoes() {
        return formacoes;
    }

    public List<String> getFerramentas() {
        return ferramentas;
    }
}
