package com.rotati;

import com.rotati.dto.QuizSubmission;
import com.rotati.model.Usuario;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FaixaEtariaTests {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void quizAceitaIdadesEntreDozeECentoEVinteAnos() {
        assertThat(violacoesDeIdade(submissionComIdade(12))).isEmpty();
        assertThat(violacoesDeIdade(submissionComIdade(120))).isEmpty();
    }

    @Test
    void quizRejeitaIdadesForaDaFaixa() {
        assertThat(violacoesDeIdade(submissionComIdade(11))).isNotEmpty();
        assertThat(violacoesDeIdade(submissionComIdade(121))).isNotEmpty();
    }

    @Test
    void entidadeUsuarioUsaAMesmaFaixaEtaria() {
        assertThat(violacoesDeIdade(new Usuario("Pessoa Teste", 120, "Escola Teste"))).isEmpty();
        assertThat(violacoesDeIdade(new Usuario("Pessoa Teste", 121, "Escola Teste"))).isNotEmpty();
    }

    private QuizSubmission submissionComIdade(int idade) {
        QuizSubmission submission = new QuizSubmission();
        submission.setNome("Pessoa Teste");
        submission.setIdade(idade);
        submission.setEscola("Escola Teste");
        return submission;
    }

    private Set<? extends ConstraintViolation<?>> violacoesDeIdade(Object objeto) {
        return validator.validate(objeto).stream()
                .filter(violacao -> violacao.getPropertyPath().toString().equals("idade"))
                .collect(java.util.stream.Collectors.toSet());
    }
}
