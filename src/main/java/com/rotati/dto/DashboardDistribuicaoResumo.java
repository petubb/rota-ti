package com.rotati.dto;

public class DashboardDistribuicaoResumo {

    private final String label;
    private final long total;
    private final double percentual;

    public DashboardDistribuicaoResumo(String label, long total, double percentual) {
        this.label = label;
        this.total = total;
        this.percentual = percentual;
    }

    public String getLabel() {
        return label;
    }

    public long getTotal() {
        return total;
    }

    public double getPercentual() {
        return percentual;
    }
}
