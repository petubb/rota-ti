package com.rotati;

import com.rotati.dto.ConteudoAreaView;
import com.rotati.model.AreaTi;
import com.rotati.service.ConteudoAreaService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConteudoAreaServiceTests {

    private final ConteudoAreaService service = new ConteudoAreaService();

    @Test
    void ofereceVideoEPersonalidadesParaTodasAsAreas() {
        for (AreaTi area : AreaTi.values()) {
            ConteudoAreaView conteudo = service.buscarPorSlug(area.getSlug());

            assertThat(conteudo.getVideos()).hasSizeBetween(1, 3);
            assertThat(conteudo.getPersonalidades()).hasSizeBetween(2, 3);
            assertThat(conteudo.getVideos().getFirst().getUrl()).startsWith("https://www.youtube.com/watch?v=");
        }
    }

    @Test
    void mantemOsDestaquesDeRondoniaSolicitados() {
        List<String> nomes = service.listarDestaquesRondonia()
                .stream()
                .map(pessoa -> pessoa.getNome())
                .toList();

        assertThat(nomes).containsExactly("Itamar Alves", "Keynes Fernandes");
    }
}
