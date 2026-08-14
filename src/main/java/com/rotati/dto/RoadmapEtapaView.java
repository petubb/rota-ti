package com.rotati.dto;

import java.util.List;

public class RoadmapEtapaView {

    private final String nivel;
    private final String titulo;
    private final String tempo;
    private final String objetivo;
    private final List<String> passos;

    public RoadmapEtapaView(String nivel, String titulo, String tempo, String objetivo, List<String> passos) {
        this.nivel = nivel;
        this.titulo = titulo;
        this.tempo = tempo;
        this.objetivo = objetivo;
        this.passos = List.copyOf(passos);
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

    public List<String> getPassos() {
        return passos;
    }
}
