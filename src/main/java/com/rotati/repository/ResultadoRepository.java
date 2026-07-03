package com.rotati.repository;

import com.rotati.model.Resultado;
import com.rotati.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    List<Resultado> findByContaOrderByCreatedAtDesc(Conta conta);

    Optional<Resultado> findFirstByContaOrderByCreatedAtDesc(Conta conta);

    List<Resultado> findTop8ByOrderByCreatedAtDesc();

    long countByContaIsNotNull();

    long countByConta(Conta conta);
}
