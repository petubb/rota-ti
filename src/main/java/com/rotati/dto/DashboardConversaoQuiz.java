package com.rotati.dto;

public class DashboardConversaoQuiz {

    private final long iniciadas;
    private final long concluidas;
    private final long naoConcluidas;
    private final long emAndamento;
    private final double taxaConclusao;

    public DashboardConversaoQuiz(
            long iniciadas,
            long concluidas,
            long naoConcluidas,
            long emAndamento,
            double taxaConclusao
    ) {
        this.iniciadas = iniciadas;
        this.concluidas = concluidas;
        this.naoConcluidas = naoConcluidas;
        this.emAndamento = emAndamento;
        this.taxaConclusao = taxaConclusao;
    }

    public long getIniciadas() {
        return iniciadas;
    }

    public long getConcluidas() {
        return concluidas;
    }

    public long getNaoConcluidas() {
        return naoConcluidas;
    }

    public long getEmAndamento() {
        return emAndamento;
    }

    public double getTaxaConclusao() {
        return taxaConclusao;
    }
}
