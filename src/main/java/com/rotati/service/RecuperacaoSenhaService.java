package com.rotati.service;

import com.rotati.model.Conta;
import com.rotati.model.TokenRecuperacaoSenha;
import com.rotati.repository.ContaRepository;
import com.rotati.repository.TokenRecuperacaoSenhaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class RecuperacaoSenhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecuperacaoSenhaService.class);

    private static final int MINUTOS_VALIDADE = 15;
    private static final int MINUTOS_ENTRE_SOLICITACOES = 2;

    private final ContaRepository contaRepository;
    private final TokenRecuperacaoSenhaRepository tokenRepository;
    private final TokenSeguroService tokenSeguroService;
    private final ContaService contaService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final String urlBase;

    public RecuperacaoSenhaService(
            ContaRepository contaRepository,
            TokenRecuperacaoSenhaRepository tokenRepository,
            TokenSeguroService tokenSeguroService,
            ContaService contaService,
            PasswordEncoder passwordEncoder,
            ApplicationEventPublisher eventPublisher,
            @Value("${app.url-base:http://127.0.0.1:8080}") String urlBase
    ) {
        this.contaRepository = contaRepository;
        this.tokenRepository = tokenRepository;
        this.tokenSeguroService = tokenSeguroService;
        this.contaService = contaService;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.urlBase = validarUrlBase(urlBase);
    }

    @Transactional
    public void solicitar(String email) {
        TokenSeguroService.TokenGerado tokenGerado = tokenSeguroService.gerar();
        String emailNormalizado = contaService.normalizarEmail(email);
        if (emailNormalizado.isEmpty() || emailNormalizado.length() > 150) {
            LOGGER.info("Solicitacao de recuperacao recebida com e-mail invalido ou vazio.");
            return;
        }

        LOGGER.info("Solicitacao de recuperacao recebida para {}.", mascararEmail(emailNormalizado));
        Conta conta = contaRepository.buscarParaAtualizacao(emailNormalizado).orElse(null);
        if (conta == null || !conta.isAtivo()) {
            LOGGER.info("Solicitacao de recuperacao finalizada sem envio para {}.", mascararEmail(emailNormalizado));
            return;
        }

        LocalDateTime agora = LocalDateTime.now();
        boolean solicitacaoRecente = tokenRepository.findFirstByContaOrderByCreatedAtDesc(conta)
                .map(token -> token.getCreatedAt().isAfter(agora.minusMinutes(MINUTOS_ENTRE_SOLICITACOES)))
                .orElse(false);
        if (solicitacaoRecente) {
            LOGGER.info("Solicitacao de recuperacao aguardando intervalo minimo para {}.",
                    mascararEmail(emailNormalizado));
            return;
        }

        tokenRepository.invalidarTokensAtivos(conta, agora);
        tokenRepository.save(new TokenRecuperacaoSenha(
                conta,
                tokenGerado.hash(),
                agora.plusMinutes(MINUTOS_VALIDADE)
        ));

        String link = UriComponentsBuilder.fromUriString(urlBase)
                .path("/recuperar-senha")
                .queryParam("token", tokenGerado.valor())
                .build()
                .encode()
                .toUriString();
        eventPublisher.publishEvent(new EventoRecuperacaoSenha(conta.getNome(), conta.getEmail(), link));
        LOGGER.info("Token de recuperacao gerado e envio agendado para {}.", mascararEmail(conta.getEmail()));
    }

    @Transactional(readOnly = true)
    public boolean tokenValido(String token) {
        if (token == null || token.isBlank() || token.length() > 100) {
            return false;
        }
        return tokenRepository.findByTokenHash(tokenSeguroService.hash(token))
                .map(item -> item.estaValido(LocalDateTime.now()))
                .orElse(false);
    }

    @Transactional
    public void redefinir(String token, String senha, String confirmarSenha) {
        contaService.validarNovaSenha(senha, confirmarSenha);
        if (token == null || token.isBlank() || token.length() > 100) {
            throw new TokenRecuperacaoException();
        }

        TokenRecuperacaoSenha recuperacao = tokenRepository
                .buscarParaAtualizacao(tokenSeguroService.hash(token))
                .orElseThrow(TokenRecuperacaoException::new);
        LocalDateTime agora = LocalDateTime.now();
        if (!recuperacao.estaValido(agora)) {
            throw new TokenRecuperacaoException();
        }

        Conta conta = recuperacao.getConta();
        if (passwordEncoder.matches(senha, conta.getSenhaHash())) {
            throw new CadastroException("senha", "A nova senha deve ser diferente da senha atual.");
        }

        conta.alterarSenha(passwordEncoder.encode(senha));
        recuperacao.marcarComoUsado(agora);
        tokenRepository.invalidarTokensAtivos(conta, agora);
    }

    private String validarUrlBase(String valor) {
        String normalizada = valor == null ? "" : valor.trim().replaceAll("/+$", "");
        URI uri = URI.create(normalizada);
        if (!("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))
                || uri.getHost() == null) {
            throw new IllegalArgumentException("APP_URL_BASE deve ser uma URL HTTP ou HTTPS valida.");
        }
        return normalizada;
    }

    private String mascararEmail(String email) {
        int arroba = email == null ? -1 : email.indexOf('@');
        if (arroba <= 1) {
            return "***";
        }
        return email.substring(0, Math.min(2, arroba)) + "***" + email.substring(arroba);
    }
}
