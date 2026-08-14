package com.rotati.dto;

import com.rotati.model.AreaTi;

import java.util.List;

public class AreaExploracaoView {

    private final AreaTi area;
    private final List<String> categorias;
    private final List<String> sinaisAfinidade;
    private final List<String> cargosIniciais;
    private final String primeiroPasso;
    private final String objetivoPrimeiroPasso;
    private final String tempoPrimeiroPasso;

    public AreaExploracaoView(
            AreaTi area,
            List<String> categorias,
            List<String> sinaisAfinidade,
            List<String> cargosIniciais,
            String primeiroPasso,
            String objetivoPrimeiroPasso,
            String tempoPrimeiroPasso
    ) {
        this.area = area;
        this.categorias = List.copyOf(categorias);
        this.sinaisAfinidade = List.copyOf(sinaisAfinidade);
        this.cargosIniciais = List.copyOf(cargosIniciais);
        this.primeiroPasso = primeiroPasso;
        this.objetivoPrimeiroPasso = objetivoPrimeiroPasso;
        this.tempoPrimeiroPasso = tempoPrimeiroPasso;
    }

    public AreaTi getArea() {
        return area;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public String getCategoriasFiltro() {
        return String.join(" ", categorias);
    }

    public List<String> getSinaisAfinidade() {
        return sinaisAfinidade;
    }

    public List<String> getCargosIniciais() {
        return cargosIniciais;
    }

    public String getPrimeiroPasso() {
        return primeiroPasso;
    }

    public String getObjetivoPrimeiroPasso() {
        return objetivoPrimeiroPasso;
    }

    public String getTempoPrimeiroPasso() {
        return tempoPrimeiroPasso;
    }
}
