package com.rotati.dto;

public class ReferenciaAreaView {

    private final String tipo;
    private final String titulo;
    private final String descricao;
    private final String url;

    public ReferenciaAreaView(String tipo, String titulo, String descricao, String url) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUrl() {
        return url;
    }
}
