package com.rotati.dto;

public class PlanoCarreiraView {

    private final String periodo;
    private final String titulo;
    private final String descricao;

    public PlanoCarreiraView(String periodo, String titulo, String descricao) {
        this.periodo = periodo;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getPeriodo() {
        return periodo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
