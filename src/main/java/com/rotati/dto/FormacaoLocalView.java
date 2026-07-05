package com.rotati.dto;

public class FormacaoLocalView {

    private final String titulo;
    private final String instituicao;
    private final String cidade;
    private final String modalidade;
    private final String descricao;
    private final String url;

    public FormacaoLocalView(
            String titulo,
            String instituicao,
            String cidade,
            String modalidade,
            String descricao,
            String url
    ) {
        this.titulo = titulo;
        this.instituicao = instituicao;
        this.cidade = cidade;
        this.modalidade = modalidade;
        this.descricao = descricao;
        this.url = url;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public String getCidade() {
        return cidade;
    }

    public String getModalidade() {
        return modalidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUrl() {
        return url;
    }
}
