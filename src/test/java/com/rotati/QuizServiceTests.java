package com.rotati;

import com.rotati.dto.AreaScore;
import com.rotati.dto.QuizSubmission;
import com.rotati.model.AreaTi;
import com.rotati.model.Pergunta;
import com.rotati.model.Resultado;
import com.rotati.repository.PerguntaRepository;
import com.rotati.repository.ResultadoRepository;
import com.rotati.service.DataInitializer;
import com.rotati.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class QuizServiceTests {

    @Autowired
    private QuizService quizService;

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private ResultadoRepository resultadoRepository;

    @Test
    void mantemDezesseisPerguntasPrincipaisEquilibradasEntreAsAreas() {
        List<Pergunta> perguntas = quizService.listarPerguntas();

        assertThat(perguntas).hasSize(16);
        for (AreaTi area : AreaTi.values()) {
            assertThat(perguntas)
                    .as("Perguntas principais de %s", area.getTitulo())
                    .filteredOn(pergunta -> pergunta.getAreaSlug().equals(area.getSlug()))
                    .hasSize(2)
                    .allMatch(pergunta -> pergunta.getPeso(area) == 3);
        }
    }

    @Test
    void inicializacaoSincronizaPerguntasExistentesSemDuplicarRegistros() {
        dataInitializer.run();
        dataInitializer.run();

        assertThat(perguntaRepository.count()).isEqualTo(24);
        assertThat(quizService.listarPerguntas()).hasSize(16);
    }

    @Test
    void respostasEquilibradasUsamDesempateQueDiferenciaTodasAsAreas() {
        List<Pergunta> perguntas = quizService.listarPerguntas();
        QuizSubmission submission = submissionComValor(perguntas, 0);

        assertThat(quizService.analisar(submission))
                .extracting(AreaScore::getCompatibilidade)
                .allMatch(valor -> valor == 50.0);

        List<Pergunta> desempate = quizService.selecionarPerguntasDesempate(submission);
        assertThat(desempate).hasSize(3);

        for (int primeira = 0; primeira < AreaTi.values().length; primeira++) {
            for (int segunda = primeira + 1; segunda < AreaTi.values().length; segunda++) {
                AreaTi primeiraArea = AreaTi.values()[primeira];
                AreaTi segundaArea = AreaTi.values()[segunda];
                assertThat(desempate)
                        .as("Cobertura de desempate entre %s e %s", primeiraArea.getTitulo(), segundaArea.getTitulo())
                        .anyMatch(pergunta -> pergunta.getPeso(primeiraArea) != pergunta.getPeso(segundaArea));
            }
        }
    }

    @Test
    void diferenciaEscalaCompletaDeRespostas() {
        List<Pergunta> perguntas = quizService.listarPerguntas();
        Pergunta perguntaDesenvolvimento = perguntas.stream()
                .filter(pergunta -> pergunta.getCodigo().equals("BASE_DEV_CRIAR"))
                .findFirst()
                .orElseThrow();

        QuizSubmission naoCombina = submissionComValor(perguntas, 0);
        naoCombina.getRespostas().put(perguntaDesenvolvimento.getId(), -2);

        QuizSubmission combinaPouco = submissionComValor(perguntas, 0);
        combinaPouco.getRespostas().put(perguntaDesenvolvimento.getId(), -1);

        QuizSubmission neutro = submissionComValor(perguntas, 0);

        QuizSubmission combinaUmPouco = submissionComValor(perguntas, 0);
        combinaUmPouco.getRespostas().put(perguntaDesenvolvimento.getId(), 1);

        QuizSubmission combinaMuito = submissionComValor(perguntas, 0);
        combinaMuito.getRespostas().put(perguntaDesenvolvimento.getId(), 2);

        double scoreNaoCombina = scoreDaArea(naoCombina, AreaTi.DESENVOLVIMENTO);
        double scoreCombinaPouco = scoreDaArea(combinaPouco, AreaTi.DESENVOLVIMENTO);
        double scoreNeutro = scoreDaArea(neutro, AreaTi.DESENVOLVIMENTO);
        double scoreCombinaUmPouco = scoreDaArea(combinaUmPouco, AreaTi.DESENVOLVIMENTO);
        double scoreCombinaMuito = scoreDaArea(combinaMuito, AreaTi.DESENVOLVIMENTO);

        assertThat(scoreNaoCombina).isLessThan(scoreCombinaPouco);
        assertThat(scoreCombinaPouco).isLessThan(scoreNeutro);
        assertThat(scoreNeutro).isLessThan(scoreCombinaUmPouco);
        assertThat(scoreCombinaUmPouco).isLessThan(scoreCombinaMuito);
    }

    @Test
    void perguntasPodemContribuirParaMaisDeUmaArea() {
        Pergunta pergunta = quizService.listarPerguntas().stream()
                .filter(item -> item.getCodigo().equals("BASE_DEV_LOGICA"))
                .findFirst()
                .orElseThrow();

        assertThat(pergunta.getPeso(AreaTi.DESENVOLVIMENTO)).isEqualTo(3);
        assertThat(pergunta.getPeso(AreaTi.DADOS)).isEqualTo(1);
        assertThat(pergunta.getPeso(AreaTi.IA)).isEqualTo(1);
        assertThat(pergunta.getPeso(AreaTi.GAME_DESIGN)).isEqualTo(1);
    }

    @Test
    void cadaAreaPodeSerIdentificadaSemDiscordarDasDemais() {
        List<Pergunta> perguntas = quizService.listarPerguntas();
        Map<AreaTi, List<String>> perguntasFortes = new LinkedHashMap<>();
        perguntasFortes.put(AreaTi.DESENVOLVIMENTO, List.of("BASE_DEV_CRIAR", "BASE_DEV_LOGICA"));
        perguntasFortes.put(AreaTi.DADOS, List.of("BASE_DADOS_ORGANIZAR", "BASE_DADOS_PADROES"));
        perguntasFortes.put(AreaTi.SEGURANCA, List.of("BASE_SEG_INVESTIGAR", "BASE_SEG_DETALHES"));
        perguntasFortes.put(AreaTi.INFRAESTRUTURA, List.of("BASE_INFRA_CONFIGURAR", "BASE_INFRA_ESTABILIDADE"));
        perguntasFortes.put(AreaTi.UX_UI, List.of("BASE_UX_INTERFACES", "BASE_UX_USUARIOS"));
        perguntasFortes.put(AreaTi.GAME_DESIGN, List.of("BASE_GAME_MECANICAS", "BASE_GAME_BALANCEAMENTO"));
        perguntasFortes.put(AreaTi.IA, List.of("BASE_IA_CURIOSIDADE", "BASE_IA_EXPERIMENTAR"));
        perguntasFortes.put(AreaTi.GESTAO, List.of("BASE_GESTAO_LIDERAR", "BASE_GESTAO_COMUNICAR"));

        perguntasFortes.forEach((area, codigos) -> {
            QuizSubmission submission = submissionComValor(perguntas, 0);
            responder(perguntas, submission, codigos, 2);

            assertThat(quizService.analisar(submission).getFirst().getArea())
                    .as("Area principal para as respostas de %s", area.getTitulo())
                    .isEqualTo(area);
            assertThat(quizService.selecionarPerguntasDesempate(submission))
                    .as("Desempate para um perfil definido de %s", area.getTitulo())
                    .isEmpty();
        });
    }

    @Test
    void perfilDeGameDesignTambemRecebeSinaisDeCaracteristicasCompartilhadas() {
        List<Pergunta> perguntas = quizService.listarPerguntas();
        QuizSubmission submission = submissionComValor(perguntas, 0);
        responder(perguntas, submission, List.of("BASE_GAME_MECANICAS", "BASE_GAME_BALANCEAMENTO"), 2);
        responder(perguntas, submission, List.of("BASE_DEV_LOGICA", "BASE_UX_USUARIOS", "BASE_IA_EXPERIMENTAR"), 1);

        assertThat(quizService.analisar(submission).getFirst().getArea()).isEqualTo(AreaTi.GAME_DESIGN);
        assertThat(scoreDaArea(submission, AreaTi.GAME_DESIGN))
                .isGreaterThan(scoreDaArea(submission, AreaTi.DESENVOLVIMENTO))
                .isGreaterThan(scoreDaArea(submission, AreaTi.UX_UI));
    }

    @Test
    void resultadoReabertoPreservaCompatibilidadeRegistradaNaTentativa() {
        List<Pergunta> perguntas = quizService.listarPerguntas();
        QuizSubmission submission = submissionComValor(perguntas, 0);
        Resultado resultado = quizService.processar(submission);

        resultado.setScore(91.0);
        resultadoRepository.save(resultado);

        assertThat(quizService.buscarResultado(resultado.getId()).getPrincipal().getCompatibilidade())
                .isEqualTo(91.0);
    }

    private QuizSubmission submissionComValor(List<Pergunta> perguntas, int valor) {
        QuizSubmission submission = new QuizSubmission();
        submission.setNome("Pessoa Teste");
        submission.setIdade(18);
        submission.setEscola("Escola de teste");

        Map<Long, Integer> respostas = new HashMap<>();
        perguntas.forEach(pergunta -> respostas.put(pergunta.getId(), valor));
        submission.setRespostas(respostas);
        return submission;
    }

    private void responder(
            List<Pergunta> perguntas,
            QuizSubmission submission,
            List<String> codigos,
            int valor
    ) {
        perguntas.stream()
                .filter(pergunta -> codigos.contains(pergunta.getCodigo()))
                .forEach(pergunta -> submission.getRespostas().put(pergunta.getId(), valor));
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
