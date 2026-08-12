package com.rotati.dto;

public class RecursoOnlineView {

    private final String tipo;
    private final String titulo;
    private final String plataforma;
    private final String custo;
    private final String descricao;
    private final String url;

    public RecursoOnlineView(
            String tipo,
            String titulo,
            String plataforma,
            String custo,
            String descricao,
            String url
    ) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.custo = custo;
        this.descricao = descricao;
        this.url = url;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public String getCusto() {
        return custo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUrl() {
        return url;
    }
}
