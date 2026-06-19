package com.rotati.dto;

import com.rotati.model.AreaTi;

public class AreaScore {

    private final AreaTi area;
    private final int pontos;
    private final double compatibilidade;

    public AreaScore(AreaTi area, int pontos, double compatibilidade) {
        this.area = area;
        this.pontos = pontos;
        this.compatibilidade = compatibilidade;
    }

    public AreaTi getArea() {
        return area;
    }

    public int getPontos() {
        return pontos;
    }

    public double getCompatibilidade() {
        return compatibilidade;
    }
}
