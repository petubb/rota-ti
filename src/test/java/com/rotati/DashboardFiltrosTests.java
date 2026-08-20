package com.rotati;

import com.rotati.dto.DashboardFiltros;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DashboardFiltrosTests {

    @Test
    void aceitaSomentePeriodosDisponiveisNoPainel() {
        DashboardFiltros filtros = DashboardFiltros.de(30, 90, 0);

        assertThat(filtros.getDiasAtividade()).isEqualTo(30);
        assertThat(filtros.getDiasResultados()).isEqualTo(90);
        assertThat(filtros.getDiasContas()).isZero();
    }

    @Test
    void restauraPadroesQuandoParametrosSaoInvalidos() {
        DashboardFiltros filtros = DashboardFiltros.de(-1, 365, 14);

        assertThat(filtros.getDiasAtividade()).isEqualTo(7);
        assertThat(filtros.getDiasResultados()).isEqualTo(30);
        assertThat(filtros.getDiasContas()).isEqualTo(30);
    }
}
