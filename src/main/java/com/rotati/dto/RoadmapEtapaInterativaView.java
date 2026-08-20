package com.rotati.dto;

import java.util.List;

public class RoadmapEtapaInterativaView {

    private final String codigo;
    private final String nivel;
    private final String titulo;
    private final String tempo;
    private final String objetivo;
    private final String explicacao;
    private final String atividade;
    private final List<String> passos;
    private final List<RoadmapRecursoView> recursos;

    public RoadmapEtapaInterativaView(
            String codigo,
            String nivel,
            String titulo,
            String tempo,
            String objetivo,
            String explicacao,
            String atividade,
            List<String> passos,
            List<RoadmapRecursoView> recursos
    ) {
        this.codigo = codigo;
        this.nivel = nivel;
        this.titulo = titulo;
        this.tempo = tempo;
        this.objetivo = objetivo;
        this.explicacao = explicacao;
        this.atividade = atividade;
        this.passos = List.copyOf(passos);
        this.recursos = List.copyOf(recursos);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNivel() {
        return nivel;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTempo() {
        return tempo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getExplicacao() {
        return explicacao;
    }

    public String getAtividade() {
        return atividade;
    }

    public List<String> getPassos() {
        return passos;
    }

    public List<RoadmapRecursoView> getRecursos() {
        return recursos;
    }
}
