package com.rotati;

import com.rotati.dto.AreaScore;
import com.rotati.dto.ResultadoView;
import com.rotati.model.AreaTi;
import com.rotati.model.Resultado;
import com.rotati.model.Usuario;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResultadoViewTests {

    @Test
    void caminhosRelacionadosNaoIncluemAreaPrincipal() {
        AreaScore principal = new AreaScore(AreaTi.INFRAESTRUTURA, 12, 82.0);
        ResultadoView view = new ResultadoView(
                new Resultado(new Usuario("Pessoa Teste", 18, "Escola Teste"), "infraestrutura-redes", 82.0),
                new Usuario("Pessoa Teste", 18, "Escola Teste"),
                principal,
                List.of(
                        principal,
                        new AreaScore(AreaTi.GAME_DESIGN, 8, 65.0),
                        new AreaScore(AreaTi.DESENVOLVIMENTO, 6, 58.0),
                        new AreaScore(AreaTi.DADOS, 4, 53.0)
                ),
                List.of(),
                "Moderada"
        );

        assertThat(view.getOutrosCaminhos())
                .extracting(item -> item.getArea().getSlug())
                .containsExactly("game-design", "desenvolvimento-software", "dados-bi");
    }
}
