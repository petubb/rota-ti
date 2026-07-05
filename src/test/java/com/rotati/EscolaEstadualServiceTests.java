package com.rotati;

import com.rotati.dto.QuizSubmission;
import com.rotati.service.EscolaEstadualService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EscolaEstadualServiceTests {

    private final EscolaEstadualService service = new EscolaEstadualService();

    @Test
    void aceitaApenasEscolaDaListaOuOpcaoOutra() {
        QuizSubmission estadual = new QuizSubmission();
        estadual.setEscola("EEEFM ORLANDO BUENO DA SILVA");

        QuizSubmission textoLivre = new QuizSubmission();
        textoLivre.setEscola("Escola digitada qualquer");

        QuizSubmission outra = new QuizSubmission();
        outra.setEscola(EscolaEstadualService.OUTRA_ESCOLA);
        outra.setEscolaOutra("Escola visitante");

        assertThat(service.escolaValida(estadual)).isTrue();
        assertThat(service.escolaValida(textoLivre)).isFalse();
        assertThat(service.escolaValida(outra)).isTrue();
    }

    @Test
    void normalizaOutraEscolaParaSalvarNomeDigitado() {
        QuizSubmission submission = new QuizSubmission();
        submission.setEscola(EscolaEstadualService.OUTRA_ESCOLA);
        submission.setEscolaOutra("  Escola Visitante  ");

        service.normalizarEscola(submission);

        assertThat(submission.getEscola()).isEqualTo("Escola Visitante");
        assertThat(submission.getEscolaOutra()).isEmpty();
    }
}
