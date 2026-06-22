package com.rotati.dto;

import java.util.List;

public class ConteudoAreaView {

    private final List<VideoRecomendado> videos;
    private final List<PersonalidadeView> personalidades;

    public ConteudoAreaView(List<VideoRecomendado> videos, List<PersonalidadeView> personalidades) {
        this.videos = List.copyOf(videos);
        this.personalidades = List.copyOf(personalidades);
    }

    public List<VideoRecomendado> getVideos() {
        return videos;
    }

    public List<PersonalidadeView> getPersonalidades() {
        return personalidades;
    }
}
