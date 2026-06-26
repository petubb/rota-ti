package com.rotati;

import com.rotati.model.AreaTi;
import com.rotati.service.DetalheAreaService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DetalheAreaServiceTests {

    private final DetalheAreaService service = new DetalheAreaService();

    @Test
    void todasAsAreasPossuemConteudoDeCarreira() {
        for (AreaTi area : AreaTi.values()) {
            var detalhe = service.buscarPorArea(area);

            assertThat(detalhe.getMercado()).isNotBlank();
            assertThat(detalhe.getPlanoCarreira()).hasSize(4);
            assertThat(detalhe.getReferencias()).hasSizeGreaterThanOrEqualTo(3);
            assertThat(detalhe.getFerramentas()).hasSizeGreaterThanOrEqualTo(4);
        }
    }

    @Test
    void todasAsAreasPossuemFonteParaFaixaSalarial() {
        for (AreaTi area : AreaTi.values()) {
            var salario = service.buscarPorArea(area).getSalario();

            assertThat(salario.getResumo()).contains("R$");
            assertThat(salario.getObservacao()).isNotBlank();
            assertThat(salario.getFontes()).isNotEmpty();
            assertThat(salario.getFontes())
                    .allSatisfy(fonte -> {
                        assertThat(fonte.getTitulo()).isNotBlank();
                        assertThat(fonte.getUrl()).startsWith("https://");
                    });
        }
    }
}
