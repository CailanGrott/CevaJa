package com.fundatec.cevaja.usuario.model.dto;

import java.time.LocalDate;

public record AdicionaNovoUsuarioOutput(
        String nome,
        String sobrenome,
        LocalDate dataNascimento,
        String cpf,
        String username,
        String senha) {
}
