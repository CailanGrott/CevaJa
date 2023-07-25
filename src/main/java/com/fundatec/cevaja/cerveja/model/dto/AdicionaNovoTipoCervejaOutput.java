package com.fundatec.cevaja.cerveja.model.dto;

import java.math.BigDecimal;

public record AdicionaNovoTipoCervejaOutput(
        String tipoCerveja,
        BigDecimal valor) {
}
