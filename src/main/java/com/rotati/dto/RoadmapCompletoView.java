package com.rotati.dto;

import java.util.List;

public class RoadmapCompletoView {

    private final List<RoadmapEtapaInterativaView> etapas;
    private final int totalPassos;

    public RoadmapCompletoView(List<RoadmapEtapaInterativaView> etapas) {
        this.etapas = List.copyOf(etapas);
        this.totalPassos = etapas.stream()
                .mapToInt(etapa -> etapa.getPassos().size())
                .sum();
    }

    public List<RoadmapEtapaInterativaView> getEtapas() {
        return etapas;
    }

    public int getTotalPassos() {
        return totalPassos;
    }
}
