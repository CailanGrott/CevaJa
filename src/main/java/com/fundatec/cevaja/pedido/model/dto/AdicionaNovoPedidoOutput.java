package com.fundatec.cevaja.pedido.model.dto;

import java.util.Set;

public record AdicionaNovoPedidoOutput(
        String username,
        Set<AdicionaCervejaToPedido> cervejas) {
}
