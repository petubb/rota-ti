package com.rotati.service;

import com.rotati.model.QuizTentativa;
import com.rotati.model.Resultado;
import com.rotati.repository.QuizTentativaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class QuizTentativaService {

    private static final String TENTATIVA_ID = "quizTentativaId";
    private static final int MINUTOS_EM_ANDAMENTO = 30;

    private final QuizTentativaRepository tentativaRepository;

    public QuizTentativaService(QuizTentativaRepository tentativaRepository) {
        this.tentativaRepository = tentativaRepository;
    }

    @Transactional
    public void iniciar(HttpSession session) {
        LocalDateTime agora = LocalDateTime.now();
        Long tentativaId = obterId(session);
        if (tentativaId != null) {
            boolean tentativaEmAberto = tentativaRepository.findById(tentativaId)
                    .filter(tentativa -> !tentativa.estaConcluida())
                    .map(tentativa -> !tentativa.getIniciadaEm().isBefore(
                            agora.minusMinutes(MINUTOS_EM_ANDAMENTO)
                    ))
                    .orElse(false);
            if (tentativaEmAberto) {
                return;
            }
        }

        QuizTentativa tentativa = tentativaRepository.save(new QuizTentativa(agora));
        session.setAttribute(TENTATIVA_ID, tentativa.getId());
    }

    @Transactional
    public void concluir(HttpSession session, Resultado resultado) {
        Long tentativaId = obterId(session);
        QuizTentativa tentativa = tentativaId == null
                ? new QuizTentativa(LocalDateTime.now())
                : tentativaRepository.findById(tentativaId)
                        .filter(registro -> !registro.estaConcluida())
                        .orElseGet(() -> new QuizTentativa(LocalDateTime.now()));

        tentativa.concluir(resultado, LocalDateTime.now());
        tentativaRepository.save(tentativa);
        session.removeAttribute(TENTATIVA_ID);
    }

    private Long obterId(HttpSession session) {
        Object valor = session.getAttribute(TENTATIVA_ID);
        return valor instanceof Long id ? id : null;
    }
}
