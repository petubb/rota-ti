package com.rotati.dto;

public class DashboardAreaResumo {

    private final String titulo;
    private final String slug;
    private final long total;
    private final double percentual;

    public DashboardAreaResumo(String titulo, String slug, long total, double percentual) {
        this.titulo = titulo;
        this.slug = slug;
        this.total = total;
        this.percentual = percentual;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSlug() {
        return slug;
    }

    public long getTotal() {
        return total;
    }

    public double getPercentual() {
        return percentual;
    }
}
