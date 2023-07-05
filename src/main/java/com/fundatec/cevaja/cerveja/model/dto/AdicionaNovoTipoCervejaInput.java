package com.fundatec.cevaja.cerveja.model.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record AdicionaNovoTipoCervejaInput(
        Integer id,
        String tipoCerveja,
        BigDecimal valor) {
}
