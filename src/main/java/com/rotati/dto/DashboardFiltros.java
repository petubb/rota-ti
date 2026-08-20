package com.rotati.dto;

import java.util.Set;

public class DashboardFiltros {

    private static final Set<Integer> PERIODOS_ATIVIDADE = Set.of(7, 14, 30);
    private static final Set<Integer> PERIODOS_LISTAS = Set.of(0, 7, 30, 90);

    private final int diasAtividade;
    private final int diasResultados;
    private final int diasContas;

    private DashboardFiltros(int diasAtividade, int diasResultados, int diasContas) {
        this.diasAtividade = normalizar(diasAtividade, PERIODOS_ATIVIDADE, 7);
        this.diasResultados = normalizar(diasResultados, PERIODOS_LISTAS, 30);
        this.diasContas = normalizar(diasContas, PERIODOS_LISTAS, 30);
    }

    public static DashboardFiltros de(int diasAtividade, int diasResultados, int diasContas) {
        return new DashboardFiltros(diasAtividade, diasResultados, diasContas);
    }

    public static DashboardFiltros padrao() {
        return new DashboardFiltros(7, 30, 30);
    }

    private static int normalizar(int valor, Set<Integer> permitidos, int padrao) {
        return permitidos.contains(valor) ? valor : padrao;
    }

    public int getDiasAtividade() {
        return diasAtividade;
    }

    public int getDiasResultados() {
        return diasResultados;
    }

    public int getDiasContas() {
        return diasContas;
    }
}
