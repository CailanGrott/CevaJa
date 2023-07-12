package com.fundatec.cevaja.usuario.model.dto;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record AdicionaNovoUsuarioInput(
        Integer id,
        String nome,
        String sobrenome,
        LocalDate dataNascimento,
        String cpf,
        String username,
        String senha) {
}
