package com.fundatec.cevaja.cerveja.model.dto;

import java.math.BigDecimal;

public record EditaTipoCervejaOutput(
        String tipoCerveja,
        BigDecimal valor) {
}
