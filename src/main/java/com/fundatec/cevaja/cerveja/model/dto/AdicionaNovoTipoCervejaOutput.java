package com.fundatec.cevaja.cerveja.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;

public record AdicionaNovoTipoCervejaOutput(
        @NotBlank(message = "Preencha o campo Tipo_Cerveja")
        @Schema(description = "Nome do tipo da cerveja", example = "IPA")
        String tipoCerveja,
        @NotBlank(message = "Preencha o campo valor")
        @Schema(description = "valor do tipo da cerveja", example = "5")
        BigDecimal valor) implements Serializable {
}