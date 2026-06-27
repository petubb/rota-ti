package com.rotati.dto;

import java.util.List;
import java.util.Map;

public class DashboardMetricas {

    private final long totalUsuarios;
    private final long totalResultados;
    private final long totalContas;
    private final long contasAtivas;
    private final long contasBloqueadas;
    private final long resultadosSalvos;
    private final long satisfacoesRecebidas;
    private final double mediaSatisfacao;
    private final double mediaScore;
    private final double taxaSalvamento;
    private final Map<String, Long> distribuicaoAreas;
    private final List<DashboardAreaResumo> areas;
    private final List<DashboardResultadoRecente> resultadosRecentes;
    private final List<DashboardContaRecente> contasRecentes;
    private final DashboardPerguntasResumo perguntas;

    public DashboardMetricas(
            long totalUsuarios,
            long totalResultados,
            long totalContas,
            long contasAtivas,
            long contasBloqueadas,
            long resultadosSalvos,
            long satisfacoesRecebidas,
            double mediaSatisfacao,
            double mediaScore,
            double taxaSalvamento,
            Map<String, Long> distribuicaoAreas,
            List<DashboardAreaResumo> areas,
            List<DashboardResultadoRecente> resultadosRecentes,
            List<DashboardContaRecente> contasRecentes,
            DashboardPerguntasResumo perguntas
    ) {
        this.totalUsuarios = totalUsuarios;
        this.totalResultados = totalResultados;
        this.totalContas = totalContas;
        this.contasAtivas = contasAtivas;
        this.contasBloqueadas = contasBloqueadas;
        this.resultadosSalvos = resultadosSalvos;
        this.satisfacoesRecebidas = satisfacoesRecebidas;
        this.mediaSatisfacao = mediaSatisfacao;
        this.mediaScore = mediaScore;
        this.taxaSalvamento = taxaSalvamento;
        this.distribuicaoAreas = Map.copyOf(distribuicaoAreas);
        this.areas = List.copyOf(areas);
        this.resultadosRecentes = List.copyOf(resultadosRecentes);
        this.contasRecentes = List.copyOf(contasRecentes);
        this.perguntas = perguntas;
    }

    public long getTotalUsuarios() {
        return totalUsuarios;
    }

    public long getTotalResultados() {
        return totalResultados;
    }

    public long getTotalContas() {
        return totalContas;
    }

    public long getContasAtivas() {
        return contasAtivas;
    }

    public long getContasBloqueadas() {
        return contasBloqueadas;
    }

    public long getResultadosSalvos() {
        return resultadosSalvos;
    }

    public long getSatisfacoesRecebidas() {
        return satisfacoesRecebidas;
    }

    public double getMediaSatisfacao() {
        return mediaSatisfacao;
    }

    public double getMediaScore() {
        return mediaScore;
    }

    public double getTaxaSalvamento() {
        return taxaSalvamento;
    }

    public Map<String, Long> getDistribuicaoAreas() {
        return distribuicaoAreas;
    }

    public List<DashboardAreaResumo> getAreas() {
        return areas;
    }

    public List<DashboardResultadoRecente> getResultadosRecentes() {
        return resultadosRecentes;
    }

    public List<DashboardContaRecente> getContasRecentes() {
        return contasRecentes;
    }

    public DashboardPerguntasResumo getPerguntas() {
        return perguntas;
    }
}
