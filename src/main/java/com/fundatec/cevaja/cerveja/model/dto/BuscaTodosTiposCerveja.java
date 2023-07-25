package com.fundatec.cevaja.cerveja.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record BuscaTodosTiposCerveja(
        Integer id,
        String tipoCerveja,
        BigDecimal valor) {
}
