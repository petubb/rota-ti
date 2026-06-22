package com.rotati.dto;

public class VideoRecomendado {

    private final String titulo;
    private final String canal;
    private final String videoId;

    public VideoRecomendado(String titulo, String canal, String videoId) {
        this.titulo = titulo;
        this.canal = canal;
        this.videoId = videoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCanal() {
        return canal;
    }

    public String getUrl() {
        return "https://www.youtube.com/watch?v=" + videoId;
    }

    public String getThumbnailUrl() {
        return "https://i.ytimg.com/vi/" + videoId + "/hqdefault.jpg";
    }
}
