package com.rotati.repository;

import com.rotati.model.Conta;
import com.rotati.model.TokenRecuperacaoSenha;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRecuperacaoSenhaRepository extends JpaRepository<TokenRecuperacaoSenha, Long> {

    Optional<TokenRecuperacaoSenha> findByTokenHash(String tokenHash);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select token from TokenRecuperacaoSenha token where token.tokenHash = :tokenHash")
    Optional<TokenRecuperacaoSenha> buscarParaAtualizacao(@Param("tokenHash") String tokenHash);

    Optional<TokenRecuperacaoSenha> findFirstByContaOrderByCreatedAtDesc(Conta conta);

    @Modifying
    @Query("""
            update TokenRecuperacaoSenha token
               set token.usadoEm = :agora
             where token.conta = :conta
               and token.usadoEm is null
            """)
    int invalidarTokensAtivos(@Param("conta") Conta conta, @Param("agora") LocalDateTime agora);
}
