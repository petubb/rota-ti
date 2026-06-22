package com.rotati.dto;

public class PersonalidadeView {

    private final String nome;
    private final String atuacao;
    private final String descricao;
    private final String url;
    private final String rotuloLink;
    private final String imagem;
    private final String iniciais;
    private final String selo;

    public PersonalidadeView(
            String nome,
            String atuacao,
            String descricao,
            String url,
            String rotuloLink,
            String imagem,
            String iniciais,
            String selo
    ) {
        this.nome = nome;
        this.atuacao = atuacao;
        this.descricao = descricao;
        this.url = url;
        this.rotuloLink = rotuloLink;
        this.imagem = imagem;
        this.iniciais = iniciais;
        this.selo = selo;
    }

    public String getNome() {
        return nome;
    }

    public String getAtuacao() {
        return atuacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getUrl() {
        return url;
    }

    public String getRotuloLink() {
        return rotuloLink;
    }

    public String getImagem() {
        return imagem;
    }

    public String getIniciais() {
        return iniciais;
    }

    public String getSelo() {
        return selo;
    }
}
