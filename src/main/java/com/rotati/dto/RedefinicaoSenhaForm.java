package com.rotati.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RedefinicaoSenhaForm {

    @NotBlank(message = "Token de recuperacao ausente.")
    @Size(max = 100, message = "Token de recuperacao invalido.")
    private String token;

    @NotBlank(message = "Crie uma nova senha.")
    @Size(min = 12, max = 72, message = "A senha deve ter entre 12 e 72 caracteres.")
    private String senha;

    @NotBlank(message = "Confirme sua nova senha.")
    @Size(max = 72, message = "A confirmacao deve ter no maximo 72 caracteres.")
    private String confirmarSenha;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }
}
