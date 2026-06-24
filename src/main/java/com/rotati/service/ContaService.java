package com.rotati.service;

import com.rotati.dto.CadastroForm;
import com.rotati.model.Conta;
import com.rotati.repository.ContaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.NoSuchElementException;
import java.nio.charset.StandardCharsets;

@Service
public class ContaService {

    public static final int LIMITE_TENTATIVAS = 5;
    public static final int MINUTOS_BLOQUEIO = 15;

    private final ContaRepository contaRepository;
    private final PasswordEncoder passwordEncoder;

    public ContaService(ContaRepository contaRepository, PasswordEncoder passwordEncoder) {
        this.contaRepository = contaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Conta criarConta(CadastroForm form) {
        validarSenhas(form);
        String nome = form.getNome().trim();
        if (nome.length() < 2 || nome.codePoints().anyMatch(Character::isISOControl)) {
            throw new CadastroException("nome", "Informe um nome valido.");
        }
        String emailNormalizado = normalizarEmail(form.getEmail());

        if (contaRepository.existsByEmailIgnoreCase(emailNormalizado)) {
            throw new CadastroException("email", "Ja existe uma conta com este e-mail.");
        }

        Conta conta = new Conta(
                nome,
                emailNormalizado,
                passwordEncoder.encode(form.getSenha())
        );

        try {
            return contaRepository.save(conta);
        } catch (DataIntegrityViolationException exception) {
            throw new CadastroException("email", "Ja existe uma conta com este e-mail.");
        }
    }

    @Transactional(readOnly = true)
    public Conta buscarPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Conta nao encontrada."));
    }

    @Transactional
    public void registrarFalhaLogin(String email) {
        String emailNormalizado = normalizarEmail(email);
        if (emailNormalizado.isEmpty() || emailNormalizado.length() > 150) {
            return;
        }
        contaRepository.buscarParaAtualizacao(emailNormalizado)
                .ifPresent(conta -> conta.registrarFalhaLogin(LIMITE_TENTATIVAS, MINUTOS_BLOQUEIO));
    }

    @Transactional
    public void registrarLoginBemSucedido(String email) {
        contaRepository.buscarParaAtualizacao(normalizarEmail(email))
                .ifPresent(Conta::registrarLoginBemSucedido);
    }

    public String normalizarEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }

    private void validarSenhas(CadastroForm form) {
        String senha = form.getSenha();
        if (senha == null) {
            throw new CadastroException("senha", "Crie uma senha.");
        }
        if (!senha.equals(form.getConfirmarSenha())) {
            throw new CadastroException("confirmarSenha", "As senhas nao coincidem.");
        }
        if (senha.chars().anyMatch(Character::isWhitespace)) {
            throw new CadastroException("senha", "A senha nao pode conter espacos.");
        }
        if (senha.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new CadastroException("senha", "A senha deve ter no maximo 72 bytes.");
        }

        boolean temMaiuscula = senha.chars().anyMatch(Character::isUpperCase);
        boolean temMinuscula = senha.chars().anyMatch(Character::isLowerCase);
        boolean temNumero = senha.chars().anyMatch(Character::isDigit);
        boolean temEspecial = senha.chars().anyMatch(caractere -> !Character.isLetterOrDigit(caractere));

        if (!temMaiuscula || !temMinuscula || !temNumero || !temEspecial) {
            throw new CadastroException(
                    "senha",
                    "Use letra maiuscula, minuscula, numero e caractere especial."
            );
        }
    }
}
