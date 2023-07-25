package com.fundatec.cevaja.usuario.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.time.LocalDate;

public record AdicionaNovoUsuarioOutput(
        @NotBlank(message = "Preencha o campo Nome")
        @Schema(description = "Nome do usuário", example = "Cailan")
        String nome,
        @NotBlank(message = "Preencha o campo Sobrenome")
        @Schema(description = "Sobrenome do usuário", example = "Soares Grott")
        String sobrenome,
        @NotBlank(message = "Preencha o campo Data_Nascimento")
        @Schema(description = "Data de nascimento do usuário", example = "2002-08-28")
        LocalDate dataNascimento,
        @NotBlank(message = "Preencha o campo CPF")
        @Schema(description = "CPF do usuário", example = "04471087002")
        String cpf,
        @NotBlank(message = "Preencha o campo Username")
        @Schema(description = "Username será usado para fazer login", example = "cailaNgtR")
        String username,
        @NotBlank(message = "Preencha o campo Senha")
        @Schema(description = "Senha será usado para fazer login", example = "Caica122014")
        String senha) implements Serializable {
}
