package com.fundatec.cevaja.pedido.model.dto;

import java.math.BigDecimal;

public record AdicionaCervejaToPedido(
        Integer id,
        String nome,
        BigDecimal valor,
        Integer quantidade) {
}
