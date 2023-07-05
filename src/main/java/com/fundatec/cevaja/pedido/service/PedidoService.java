package com.fundatec.cevaja.pedido.service;

import com.fundatec.cevaja.pedido.model.Pedido;
import com.fundatec.cevaja.pedido.repository.PedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(Pedido pedido) {
        return pedidoRepository.saveAndFlush(pedido);
    }
}
