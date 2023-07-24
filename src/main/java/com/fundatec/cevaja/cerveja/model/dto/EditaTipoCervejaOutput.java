package com.fundatec.cevaja.cerveja.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;

public record EditaTipoCervejaOutput(
        @NotBlank(message = "Preencha o campo Tipo_Cerveja")
        @Schema(description = "Nome do tipo da cerveja", example = "APA")
        String tipoCerveja,
        @NotBlank(message = "Preencha o campo valor")
        @Schema(description = "valor do tipo da cerveja", example = "7.99")
        BigDecimal valor) implements Serializable {
}
