package com.rotati;

import com.rotati.dto.AreaScore;
import com.rotati.dto.QuizSubmission;
import com.rotati.model.AreaTi;
import com.rotati.model.Pergunta;
import com.rotati.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuizServiceTests {

    @Autowired
    private QuizService quizService;

    @Test
    void mantemDezoitoPerguntasPrincipaisEUsaDesempateQuandoNecessario() {
        List<Pergunta> perguntas = quizService.listarPerguntas();
        QuizSubmission submission = submissionComValor(perguntas, 0);

        assertThat(perguntas).hasSize(18);
        assertThat(quizService.analisar(submission))
                .extracting(AreaScore::getCompatibilidade)
                .allMatch(valor -> valor == 50.0);
        assertThat(quizService.selecionarPerguntasDesempate(submission)).hasSize(2);
    }

    @Test
    void diferenciaDiscordanciaNeutralidadeEConcordancia() {
        List<Pergunta> perguntas = quizService.listarPerguntas();
        Pergunta perguntaDesenvolvimento = perguntas.stream()
                .filter(pergunta -> pergunta.getCodigo().equals("BASE_DEV_CRIAR"))
                .findFirst()
                .orElseThrow();

        QuizSubmission discordo = submissionComValor(perguntas, 0);
        discordo.getRespostas().put(perguntaDesenvolvimento.getId(), -1);

        QuizSubmission neutro = submissionComValor(perguntas, 0);

        QuizSubmission concordo = submissionComValor(perguntas, 0);
        concordo.getRespostas().put(perguntaDesenvolvimento.getId(), 1);

        double scoreDiscordo = scoreDaArea(discordo, AreaTi.DESENVOLVIMENTO);
        double scoreNeutro = scoreDaArea(neutro, AreaTi.DESENVOLVIMENTO);
        double scoreConcordo = scoreDaArea(concordo, AreaTi.DESENVOLVIMENTO);

        assertThat(scoreDiscordo).isLessThan(scoreNeutro);
        assertThat(scoreNeutro).isLessThan(scoreConcordo);
    }

    @Test
    void perguntasPodemContribuirParaMaisDeUmaArea() {
        Pergunta pergunta = quizService.listarPerguntas().stream()
                .filter(item -> item.getCodigo().equals("BASE_DEV_LOGICA"))
                .findFirst()
                .orElseThrow();

        assertThat(pergunta.getPeso(AreaTi.DESENVOLVIMENTO)).isEqualTo(2);
        assertThat(pergunta.getPeso(AreaTi.DADOS)).isEqualTo(1);
        assertThat(pergunta.getPeso(AreaTi.IA)).isEqualTo(1);
    }

    @Test
    void perfilDefinidoNaoExigeDesempate() {
        List<Pergunta> perguntas = quizService.listarPerguntas();
        QuizSubmission submission = submissionComValor(perguntas, -1);

        perguntas.stream()
                .filter(pergunta -> pergunta.getPeso(AreaTi.DESENVOLVIMENTO) > 0)
                .forEach(pergunta -> submission.getRespostas().put(pergunta.getId(), 1));

        assertThat(quizService.analisar(submission).getFirst().getArea()).isEqualTo(AreaTi.DESENVOLVIMENTO);
        assertThat(quizService.selecionarPerguntasDesempate(submission)).isEmpty();
    }

    private QuizSubmission submissionComValor(List<Pergunta> perguntas, int valor) {
        QuizSubmission submission = new QuizSubmission();
        submission.setIdade(18);
        submission.setEscola("Escola de teste");

        Map<Long, Integer> respostas = new HashMap<>();
        perguntas.forEach(pergunta -> respostas.put(pergunta.getId(), valor));
        submission.setRespostas(respostas);
        return submission;
    }

    private double scoreDaArea(QuizSubmission submission, AreaTi area) {
        return quizService.analisar(submission)
                .stream()
                .filter(score -> score.getArea() == area)
                .findFirst()
                .map(AreaScore::getCompatibilidade)
                .orElseThrow();
    }
}
