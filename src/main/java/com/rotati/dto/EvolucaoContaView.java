package com.rotati.dto;

import com.rotati.model.AreaTi;

import java.util.List;

public class EvolucaoContaView {

    private final List<ResultadoHistoricoView> resultados;
    private final int areasExploradas;
    private final AreaTi areaRecorrente;
    private final long recorrenciasDaArea;

    public EvolucaoContaView(
            List<ResultadoHistoricoView> resultados,
            int areasExploradas,
            AreaTi areaRecorrente,
            long recorrenciasDaArea
    ) {
        this.resultados = List.copyOf(resultados);
        this.areasExploradas = areasExploradas;
        this.areaRecorrente = areaRecorrente;
        this.recorrenciasDaArea = recorrenciasDaArea;
    }

    public List<ResultadoHistoricoView> getResultados() {
        return resultados;
    }

    public int getTotalTestes() {
        return resultados.size();
    }

    public int getAreasExploradas() {
        return areasExploradas;
    }

    public AreaTi getAreaRecorrente() {
        return areaRecorrente;
    }

    public long getRecorrenciasDaArea() {
        return recorrenciasDaArea;
    }

    public ResultadoHistoricoView getMaisRecente() {
        return resultados.isEmpty() ? null : resultados.getFirst();
    }

    public ResultadoHistoricoView getAnterior() {
        return resultados.size() < 2 ? null : resultados.get(1);
    }

    public boolean isVazia() {
        return resultados.isEmpty();
    }

    public boolean isPossuiComparacao() {
        return resultados.size() >= 2;
    }

    public boolean isManteveAreaRecente() {
        return isPossuiComparacao() && getMaisRecente().getArea() == getAnterior().getArea();
    }

    public double getVariacaoScoreRecente() {
        if (!isManteveAreaRecente()) {
            return 0;
        }
        return getMaisRecente().getScore() - getAnterior().getScore();
    }

    public double getVariacaoScoreRecenteAbsoluta() {
        return Math.abs(getVariacaoScoreRecente());
    }

    public boolean isScoreRecenteSubiu() {
        return getVariacaoScoreRecente() > 0;
    }

    public boolean isScoreRecenteCaiu() {
        return getVariacaoScoreRecente() < 0;
    }

    public boolean isScoreRecenteEstavel() {
        return isManteveAreaRecente() && Double.compare(getVariacaoScoreRecente(), 0) == 0;
    }
}
