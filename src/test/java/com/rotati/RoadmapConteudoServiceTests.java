package com.rotati;

import com.rotati.model.AreaTi;
import com.rotati.service.DetalheAreaService;
import com.rotati.service.RoadmapConteudoService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoadmapConteudoServiceTests {

    private final RoadmapConteudoService service =
            new RoadmapConteudoService(new DetalheAreaService());

    @Test
    void todasAsAreasPossuemRoadmapCompletoEInterativo() {
        for (AreaTi area : AreaTi.values()) {
            var roadmap = service.buscarPorArea(area);

            assertThat(roadmap.getEtapas()).hasSize(6);
            assertThat(roadmap.getTotalPassos()).isEqualTo(18);
            assertThat(roadmap.getEtapas())
                    .allSatisfy(etapa -> {
                        assertThat(etapa.getCodigo()).startsWith("etapa-");
                        assertThat(etapa.getNivel()).isNotBlank();
                        assertThat(etapa.getTitulo()).isNotBlank();
                        assertThat(etapa.getTempo()).isNotBlank();
                        assertThat(etapa.getObjetivo()).isNotBlank();
                        assertThat(etapa.getExplicacao()).hasSizeGreaterThan(100);
                        assertThat(etapa.getAtividade()).isNotBlank();
                        assertThat(etapa.getPassos()).hasSize(3);
                        assertThat(etapa.getRecursos()).hasSizeGreaterThanOrEqualTo(2);
                    });
        }
    }

    @Test
    void todosOsRecursosUsamLinksSeguros() {
        for (AreaTi area : AreaTi.values()) {
            var recursos = service.buscarPorArea(area).getEtapas().stream()
                    .flatMap(etapa -> etapa.getRecursos().stream())
                    .toList();

            assertThat(recursos).allSatisfy(recurso -> {
                assertThat(recurso.getTipo()).isNotBlank();
                assertThat(recurso.getTitulo()).isNotBlank();
                assertThat(recurso.getDescricao()).isNotBlank();
                assertThat(recurso.getUrl()).startsWith("https://");
            });
        }
    }
}
