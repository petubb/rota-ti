package com.rotati.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;

public class RotaPasswordEncoder implements PasswordEncoder {

    private static final int MAXIMO_BYTES_BCRYPT = 72;

    private final BCryptPasswordEncoder delegate;

    public RotaPasswordEncoder(int custo) {
        this.delegate = new BCryptPasswordEncoder(custo);
    }

    @Override
    public String encode(CharSequence senha) {
        if (senha == null || excedeLimite(senha)) {
            throw new IllegalArgumentException("Senha fora do limite do BCrypt.");
        }
        return delegate.encode(senha);
    }

    @Override
    public boolean matches(CharSequence senha, String hash) {
        return senha != null && !excedeLimite(senha) && delegate.matches(senha, hash);
    }

    @Override
    public boolean upgradeEncoding(String hash) {
        return delegate.upgradeEncoding(hash);
    }

    private boolean excedeLimite(CharSequence senha) {
        return senha.toString().getBytes(StandardCharsets.UTF_8).length > MAXIMO_BYTES_BCRYPT;
    }
}
