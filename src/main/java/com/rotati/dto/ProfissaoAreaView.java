package com.rotati.dto;

public class ProfissaoAreaView {

    private final String titulo;
    private final String nivel;
    private final String descricao;

    public ProfissaoAreaView(String titulo, String nivel, String descricao) {
        this.titulo = titulo;
        this.nivel = nivel;
        this.descricao = descricao;
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
}
