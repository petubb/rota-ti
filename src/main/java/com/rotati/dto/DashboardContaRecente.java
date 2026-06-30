package com.rotati.dto;

public class DashboardContaRecente {

    private final Long id;
    private final String nome;
    private final String email;
    private final String papel;
    private final boolean ativo;
    private final boolean bloqueada;
    private final long resultadosSalvos;
    private final String criadaEm;

    public DashboardContaRecente(
            Long id,
            String nome,
            String email,
            String papel,
            boolean ativo,
            boolean bloqueada,
            long resultadosSalvos,
            String criadaEm
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.papel = papel;
        this.ativo = ativo;
        this.bloqueada = bloqueada;
        this.resultadosSalvos = resultadosSalvos;
        this.criadaEm = criadaEm;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPapel() {
        return papel;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isBloqueada() {
        return bloqueada;
    }

    public long getResultadosSalvos() {
        return resultadosSalvos;
    }

    public String getCriadaEm() {
        return criadaEm;
    }
}
