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
            assertThat(detalhe.getProfissoes()).hasSizeGreaterThanOrEqualTo(4);
            assertThat(detalhe.getFormacoes()).hasSizeGreaterThanOrEqualTo(4);
            assertThat(detalhe.getFerramentas()).hasSizeGreaterThanOrEqualTo(4);
        }
    }

    @Test
    void todasAsAreasPossuemFonteParaFaixaSalarial() {
        for (AreaTi area : AreaTi.values()) {
            var salario = service.buscarPorArea(area).getSalario();

            assertThat(salario.getResumo()).contains("R$");
            assertThat(salario.getCargoReferencia()).isNotBlank();
            assertThat(salario.getEscopo()).isNotBlank();
            assertThat(salario.getCompetencia()).isNotBlank();
            assertThat(salario.getNatureza()).isNotBlank();
            assertThat(salario.getObservacao()).isNotBlank();
            assertThat(salario.getMetodologia()).isNotBlank();
            assertThat(salario.getFaixas()).hasSize(3)
                    .allSatisfy(faixa -> {
                        assertThat(faixa.getRotulo()).isNotBlank();
                        assertThat(faixa.getValor()).contains("R$");
                        assertThat(faixa.getLeitura()).isNotBlank();
                    });
            assertThat(salario.getFontes()).isNotEmpty();
            assertThat(salario.getFontes())
                    .allSatisfy(fonte -> {
                        assertThat(fonte.getTitulo()).isNotBlank();
                        assertThat(fonte.getDescricao()).isNotBlank();
                        assertThat(fonte.getUrl()).startsWith("https://");
                    });
        }
    }

    @Test
    void diferenciaFaixasPorSenioridadeDeQuartisEstatisticos() {
        var desenvolvimento = service.buscarPorArea(AreaTi.DESENVOLVIMENTO).getSalario();
        var ux = service.buscarPorArea(AreaTi.UX_UI).getSalario();

        assertThat(desenvolvimento.getFaixas())
                .extracting(faixa -> faixa.getRotulo())
                .containsExactly("Junior", "Pleno", "Senior");
        assertThat(ux.getFaixas())
                .extracting(faixa -> faixa.getRotulo())
                .containsExactly("Quartil inferior", "Mediana", "Quartil superior");
        assertThat(ux.getMetodologia()).contains("nao equivalem automaticamente a junior, pleno e senior");
    }
}
