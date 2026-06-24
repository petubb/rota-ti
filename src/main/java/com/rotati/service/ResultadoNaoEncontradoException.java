package com.rotati.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResultadoNaoEncontradoException extends RuntimeException {

    public ResultadoNaoEncontradoException() {
        super("Resultado nao encontrado.");
    }
}
