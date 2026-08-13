package com.rotati.dto;

public class FonteSalarioView {

    private final String titulo;
    private final String descricao;
    private final String url;

    public FonteSalarioView(String titulo, String descricao, String url) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
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
