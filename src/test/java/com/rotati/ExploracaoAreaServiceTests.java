package com.rotati;

import com.rotati.model.AreaTi;
import com.rotati.service.DetalheAreaService;
import com.rotati.service.ExploracaoAreaService;
import com.rotati.service.RoadmapConteudoService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExploracaoAreaServiceTests {

    private final DetalheAreaService detalheAreaService = new DetalheAreaService();
    private final ExploracaoAreaService service = new ExploracaoAreaService(
            detalheAreaService,
            new RoadmapConteudoService(detalheAreaService)
    );

    @Test
    void listaTodasAsAreasComInformacoesParaComparacao() {
        var areas = service.listar();

        assertThat(areas).hasSize(AreaTi.values().length);
        assertThat(areas).allSatisfy(exploracao -> {
            assertThat(exploracao.getCategorias()).hasSizeGreaterThanOrEqualTo(2);
            assertThat(exploracao.getCategoriasFiltro()).isNotBlank();
            assertThat(exploracao.getSinaisAfinidade()).hasSize(3);
            assertThat(exploracao.getCargosIniciais()).hasSize(2);
            assertThat(exploracao.getPrimeiroPasso()).isNotBlank();
            assertThat(exploracao.getObjetivoPrimeiroPasso()).isNotBlank();
            assertThat(exploracao.getTempoPrimeiroPasso()).isNotBlank();
        });
    }

    @Test
    void permiteBuscarOResumoDeUmaAreaEspecifica() {
        var exploracao = service.buscarPorArea(AreaTi.SEGURANCA);

        assertThat(exploracao.getArea()).isEqualTo(AreaTi.SEGURANCA);
        assertThat(exploracao.getCategorias()).contains("analisar", "proteger");
        assertThat(exploracao.getCargosIniciais()).contains("Analista SOC");
        assertThat(exploracao.getPrimeiroPasso()).isEqualTo("Redes e sistemas");
    }
}
