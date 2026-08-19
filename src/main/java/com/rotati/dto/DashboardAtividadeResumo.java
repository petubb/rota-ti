package com.rotati.dto;

public class DashboardAtividadeResumo {

    private final String dia;
    private final String data;
    private final long total;
    private final double altura;

    public DashboardAtividadeResumo(String dia, String data, long total, double altura) {
        this.dia = dia;
        this.data = data;
        this.total = total;
        this.altura = altura;
    }

    public String getDia() {
        return dia;
    }

    public String getData() {
        return data;
    }

    public long getTotal() {
        return total;
    }

    public double getAltura() {
        return altura;
    }
}
