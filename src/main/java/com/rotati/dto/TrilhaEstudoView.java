package com.rotati.dto;

public class TrilhaEstudoView {

    private final String etapa;
    private final String titulo;
    private final String descricao;
    private final String pratica;

    public TrilhaEstudoView(String etapa, String titulo, String descricao, String pratica) {
        this.etapa = etapa;
        this.titulo = titulo;
        this.descricao = descricao;
        this.pratica = pratica;
    }

    public String getEtapa() {
        return etapa;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPratica() {
        return pratica;
    }
}
