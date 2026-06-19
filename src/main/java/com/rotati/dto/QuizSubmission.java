package com.rotati.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashMap;
import java.util.Map;

public class QuizSubmission {

    @NotNull(message = "Informe sua idade.")
    @Min(value = 12, message = "A idade minima esperada e 12 anos.")
    @Max(value = 25, message = "A idade maxima esperada e 25 anos.")
    private Integer idade;

    @NotBlank(message = "Informe sua escola.")
    @Size(max = 100, message = "O nome da escola deve ter no maximo 100 caracteres.")
    private String escola;

    private Map<Long, Integer> respostas = new HashMap<>();

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public Map<Long, Integer> getRespostas() {
        return respostas;
    }

    public void setRespostas(Map<Long, Integer> respostas) {
        this.respostas = respostas;
    }
}
