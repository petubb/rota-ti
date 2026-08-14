package com.rotati.dto;

public class FaixaSalarioView {

    private final String rotulo;
    private final String valor;
    private final String leitura;

    public FaixaSalarioView(String rotulo, String valor, String leitura) {
        this.rotulo = rotulo;
        this.valor = valor;
        this.leitura = leitura;
    }

    public String getRotulo() {
        return rotulo;
    }

    public String getValor() {
        return valor;
    }

    public String getLeitura() {
        return leitura;
    }
}
