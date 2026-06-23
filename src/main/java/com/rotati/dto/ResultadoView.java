package com.rotati.dto;

import com.rotati.model.Resultado;
import com.rotati.model.Usuario;

import java.util.List;

public class ResultadoView {

    private final Resultado resultado;
    private final Usuario usuario;
    private final AreaScore principal;
    private final List<AreaScore> ranking;
    private final List<String> destaquesPerfil;
    private final String confianca;

    public ResultadoView(
            Resultado resultado,
            Usuario usuario,
            AreaScore principal,
            List<AreaScore> ranking,
            List<String> destaquesPerfil,
            String confianca
    ) {
        this.resultado = resultado;
        this.usuario = usuario;
        this.principal = principal;
        this.ranking = ranking;
        this.destaquesPerfil = destaquesPerfil;
        this.confianca = confianca;
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

    public List<AreaScore> getTopTres() {
        return ranking.stream().limit(3).toList();
    }

    public List<String> getDestaquesPerfil() {
        return destaquesPerfil;
    }

    public String getConfianca() {
        return confianca;
    }
}
