package com.rotati.dto;

public class DashboardPerguntasResumo {

    private final long total;
    private final long base;
    private final long desempate;

    public DashboardPerguntasResumo(long total, long base, long desempate) {
        this.total = total;
        this.base = base;
        this.desempate = desempate;
    }

    public long getTotal() {
        return total;
    }

    public long getBase() {
        return base;
    }

    public long getDesempate() {
        return desempate;
    }
}
