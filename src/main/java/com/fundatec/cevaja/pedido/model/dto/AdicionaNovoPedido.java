package com.fundatec.cevaja.pedido.model.dto;

import java.util.Collection;

public record AdicionaNovoPedido(String username, Collection<Cerveja> cervejas) {
    public record Cerveja(String tipoCerveja, Integer quantidade) {
    }
}
