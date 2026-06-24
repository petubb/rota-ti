package com.rotati.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class EmailRecuperacaoSenhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailRecuperacaoSenhaService.class);

    private final ObjectProvider<JavaMailSender> mailSenderProvider;
    private final String remetente;
    private final String host;

    public EmailRecuperacaoSenhaService(
            ObjectProvider<JavaMailSender> mailSenderProvider,
            @Value("${app.email.remetente:}") String remetente,
            @Value("${spring.mail.host:}") String host
    ) {
        this.mailSenderProvider = mailSenderProvider;
        this.remetente = remetente;
        this.host = host;
    }

    public boolean estaConfigurado() {
        return !host.isBlank() && !remetente.isBlank() && mailSenderProvider.getIfAvailable() != null;
    }

    @Async("recuperacaoSenhaExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void enviar(EventoRecuperacaoSenha evento) {
        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        if (mailSender == null || host.isBlank() || remetente.isBlank()) {
            LOGGER.warn("Recuperacao de senha solicitada, mas o SMTP nao esta configurado.");
            return;
        }

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(remetente);
        mensagem.setTo(evento.email());
        mensagem.setSubject("Redefinicao de senha | Rota TI");
        mensagem.setText("""
                Ola, %s.

                Recebemos uma solicitacao para redefinir sua senha no Rota TI.
                Use o link abaixo em ate 15 minutos:

                %s

                Se voce nao fez essa solicitacao, ignore este e-mail.
                """.formatted(evento.nome(), evento.link()));

        try {
            mailSender.send(mensagem);
        } catch (MailException exception) {
            LOGGER.error("Nao foi possivel enviar o e-mail de recuperacao de senha.");
        }
    }
}
