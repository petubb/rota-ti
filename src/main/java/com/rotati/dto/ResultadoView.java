package com.rotati.dto;

import com.rotati.model.Resultado;
import com.rotati.model.Usuario;

import java.util.List;

public class ResultadoView {

    private final Resultado resultado;
    private final Usuario usuario;
    private final AreaScore principal;
    private final List<AreaScore> ranking;

    public ResultadoView(Resultado resultado, Usuario usuario, AreaScore principal, List<AreaScore> ranking) {
        this.resultado = resultado;
        this.usuario = usuario;
        this.principal = principal;
        this.ranking = ranking;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public AreaScore getPrincipal() {
        return principal;
    }

    public List<AreaScore> getRanking() {
        return ranking;
    }
}
