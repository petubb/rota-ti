package com.rotati.dto;

public class FonteSalarioView {

    private final String titulo;
    private final String url;

    public FonteSalarioView(String titulo, String url) {
        this.titulo = titulo;
        this.url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUrl() {
        return url;
    }
}
