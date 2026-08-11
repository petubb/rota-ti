package com.rotati.dto;

import com.rotati.model.AreaTi;

public class AreaRelacionadaView {

    private final AreaScore score;
    private final String justificativa;

    public AreaRelacionadaView(AreaScore score, String justificativa) {
        this.score = score;
        this.justificativa = justificativa;
    }

    public AreaTi getArea() {
        return score.getArea();
    }

    public int getPontos() {
        return score.getPontos();
    }

    public double getCompatibilidade() {
        return score.getCompatibilidade();
    }

    public String getJustificativa() {
        return justificativa;
    }
}
