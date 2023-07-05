package com.fundatec.cevaja.model.dto;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record AdicionaNovoUsuarioInput(
        Integer id,
        String nome,
        String sobrenome,
        LocalDate dataNascimento,
        String cpf,
        String login,
        String senha) {
}
