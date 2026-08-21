package com.rotati.service;

import com.rotati.dto.QuizSubmission;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EscolaEstadualService {

    public static final String OUTRA_ESCOLA = "Outra escola";
    public static final String NAO_ESTOU_NA_ESCOLA = "N\u00e3o estou na escola";

    private static final List<EscolaEstadual> ESCOLAS = List.of(
            new EscolaEstadual("CEEJA - GLICERIA MARIA DE OLIVEIRA CRIVELLI", "Rede estadual / Urbana"),
            new EscolaEstadual("CENTRO TECNICO ESTADUAL DE EDUCACAO RURAL ABAITARA", "Rede estadual / Rural"),
            new EscolaEstadual("EEEF ANISIO SERRAO DE CARVALHO", "Rede estadual / Urbana"),
            new EscolaEstadual("EEEFM BOM SUCESSO", "Rede estadual / Urbana"),
            new EscolaEstadual("EEEFM MARECHAL CORDEIRO DE FARIAS", "Rede estadual / Urbana"),
            new EscolaEstadual("EEEFM ORLANDO BUENO DA SILVA", "Rede estadual / Urbana"),
            new EscolaEstadual("EEEFM PROFESSOR VALDIR MONFREDINHO", "Rede estadual / Urbana"),
            new EscolaEstadual("EEEFM RAIMUNDO EUCLIDES BARBOSA", "Rede estadual / Urbana")
    ).stream()
            .sorted(Comparator.comparing(EscolaEstadual::nome))
            .toList();

    private static final Set<String> NOMES_NORMALIZADOS = ESCOLAS.stream()
            .map(escola -> normalizar(escola.nome()))
            .collect(Collectors.toUnmodifiableSet());

    public List<EscolaEstadual> listar() {
        return ESCOLAS;
    }

    public boolean usaOutraEscola(QuizSubmission submission) {
        return OUTRA_ESCOLA.equals(limpar(submission.getEscola()));
    }

    public boolean escolaCadastrada(String escola) {
        String nomeNormalizado = normalizar(escola);
        return normalizar(NAO_ESTOU_NA_ESCOLA).equals(nomeNormalizado)
                || NOMES_NORMALIZADOS.contains(nomeNormalizado);
    }

    public boolean escolaValida(QuizSubmission submission) {
        if (usaOutraEscola(submission)) {
            return !limpar(submission.getEscolaOutra()).isBlank();
        }
        return escolaCadastrada(submission.getEscola());
    }

    public void normalizarEscola(QuizSubmission submission) {
        if (usaOutraEscola(submission)) {
            submission.setEscola(limpar(submission.getEscolaOutra()));
            submission.setEscolaOutra("");
            return;
        }
        submission.setEscola(limpar(submission.getEscola()));
        submission.setEscolaOutra("");
    }

    public QuizSubmission preencherEscolaParaFormulario(QuizSubmission submission) {
        String escola = limpar(submission.getEscola());
        if (!escola.isBlank() && !escolaCadastrada(escola)) {
            submission.setEscola(OUTRA_ESCOLA);
            submission.setEscolaOutra(escola);
        }
        return submission;
    }

    private static String limpar(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private static String normalizar(String valor) {
        return limpar(valor).toUpperCase(Locale.ROOT);
    }

    public record EscolaEstadual(String nome, String rede) {
    }
}
