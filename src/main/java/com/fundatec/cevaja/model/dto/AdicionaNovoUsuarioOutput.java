package com.fundatec.cevaja.model.dto;

import java.time.LocalDate;

public record AdicionaNovoUsuarioOutput(
        String nome,
        String sobrenome,
        LocalDate dataNascimento,
        String cpf,
        String login,
        String senha) {
}
