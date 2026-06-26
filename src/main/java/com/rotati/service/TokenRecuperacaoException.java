package com.rotati.service;

public class TokenRecuperacaoException extends RuntimeException {

    public TokenRecuperacaoException() {
        super("O link de recuperacao e invalido ou expirou.");
    }
}
