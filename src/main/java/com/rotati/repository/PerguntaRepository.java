package com.rotati.repository;

import com.rotati.model.Pergunta;
import com.rotati.model.TipoPergunta;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

    @EntityGraph(attributePaths = "pesos")
    List<Pergunta> findAllByOrderByIdAsc();

    @EntityGraph(attributePaths = "pesos")
    List<Pergunta> findAllByTipoOrderByIdAsc(TipoPergunta tipo);

    long countByTipo(TipoPergunta tipo);
}
