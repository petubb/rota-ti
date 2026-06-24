package com.rotati.service;

public class CadastroException extends RuntimeException {

    private final String campo;

    public CadastroException(String campo, String mensagem) {
        super(mensagem);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}
