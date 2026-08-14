package com.rotati.dto;

public class ProjetoPraticoView {

    private final String titulo;
    private final String nivel;
    private final String descricao;
    private final String entregavel;

    public ProjetoPraticoView(String titulo, String nivel, String descricao, String entregavel) {
        this.titulo = titulo;
        this.nivel = nivel;
        this.descricao = descricao;
        this.entregavel = entregavel;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getNivel() {
        return nivel;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEntregavel() {
        return entregavel;
    }
}
