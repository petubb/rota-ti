package com.rotati;

import com.rotati.model.QuizTentativa;
import com.rotati.model.Resultado;
import com.rotati.model.Usuario;
import com.rotati.repository.QuizTentativaRepository;
import com.rotati.repository.ResultadoRepository;
import com.rotati.repository.UsuarioRepository;
import com.rotati.service.QuizTentativaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QuizTentativaServiceTests {

    @Autowired
    private QuizTentativaService tentativaService;

    @Autowired
    private QuizTentativaRepository tentativaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Test
    void recarregarQuizNaMesmaSessaoNaoDuplicaTentativaAberta() {
        MockHttpSession session = new MockHttpSession();
        long antes = tentativaRepository.count();

        tentativaService.iniciar(session);
        tentativaService.iniciar(session);

        assertThat(tentativaRepository.count()).isEqualTo(antes + 1);
    }

    @Test
    void conclusaoVinculaResultadoELiberaSessaoParaNovoQuiz() {
        MockHttpSession session = new MockHttpSession();
        tentativaService.iniciar(session);
        Usuario usuario = usuarioRepository.save(new Usuario("Pessoa Teste", 18, "Escola Teste"));
        Resultado resultado = resultadoRepository.save(new Resultado(usuario, "desenvolvimento-software", 80.0));

        tentativaService.concluir(session, resultado);

        QuizTentativa tentativa = tentativaRepository.findAll().stream()
                .filter(item -> resultado.getId().equals(item.getResultado().getId()))
                .findFirst()
                .orElseThrow();
        assertThat(tentativa.estaConcluida()).isTrue();
        assertThat(tentativa.getConcluidaEm()).isNotNull();

        long aposConclusao = tentativaRepository.count();
        tentativaService.iniciar(session);
        assertThat(tentativaRepository.count()).isEqualTo(aposConclusao + 1);
    }
}
