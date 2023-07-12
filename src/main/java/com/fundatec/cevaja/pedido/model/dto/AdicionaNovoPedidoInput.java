package com.fundatec.cevaja.pedido.model.dto;

import java.math.BigDecimal;
import java.util.Set;

public record AdicionaNovoPedidoInput(
        String username,
        Set<AdicionaCervejaToPedido> cervejas,
        BigDecimal valor) {
}
