package com.rotati.dto;

import com.rotati.model.AreaTi;

import java.time.LocalDateTime;

public class ResultadoHistoricoView {

    private final Long id;
    private final AreaTi area;
    private final double score;
    private final LocalDateTime createdAt;

    public ResultadoHistoricoView(Long id, AreaTi area, double score, LocalDateTime createdAt) {
        this.id = id;
        this.area = area;
        this.score = score;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public AreaTi getArea() {
        return area;
    }

    public double getScore() {
        return score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
