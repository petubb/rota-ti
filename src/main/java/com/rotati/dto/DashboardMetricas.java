package com.rotati.dto;

import java.util.Map;

public class DashboardMetricas {

    private final long totalUsuarios;
    private final long totalResultados;
    private final double mediaSatisfacao;
    private final Map<String, Long> distribuicaoAreas;

    public DashboardMetricas(long totalUsuarios, long totalResultados, double mediaSatisfacao, Map<String, Long> distribuicaoAreas) {
        this.totalUsuarios = totalUsuarios;
        this.totalResultados = totalResultados;
        this.mediaSatisfacao = mediaSatisfacao;
        this.distribuicaoAreas = distribuicaoAreas;
    }

    public long getTotalUsuarios() {
        return totalUsuarios;
    }

    public long getTotalResultados() {
        return totalResultados;
    }

    public double getMediaSatisfacao() {
        return mediaSatisfacao;
    }

    public Map<String, Long> getDistribuicaoAreas() {
        return distribuicaoAreas;
    }
}
