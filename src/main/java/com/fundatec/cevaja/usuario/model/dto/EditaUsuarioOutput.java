package com.fundatec.cevaja.usuario.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record EditaUsuarioOutput(
        @NotBlank(message = "Preencha o campo Nome")
        @Schema(description = "Nome do usuário", example = "Jorger Dyogo")
        String nome,
        @NotBlank(message = "Preencha o campo Sobrenome")
        @Schema(description = "Sobrenome do usuário", example = "de Brito")
        String sobrenome) implements Serializable {
}
