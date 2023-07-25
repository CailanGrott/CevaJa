package com.fundatec.cevaja.pedido.service;

import com.fundatec.cevaja.cerveja.model.Cerveja;
import com.fundatec.cevaja.pedido.model.ItemPedido;
import com.fundatec.cevaja.pedido.model.Pedido;
import com.fundatec.cevaja.pedido.repository.ItemPedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    /**
     * Associa pedidos aos produtos.
     *
     */

    public void associarPedidosAoProduto(Cerveja cerveja,
                                         Integer quantidadeCerveja,
                                         Pedido pedido) {
        itemPedidoRepository.save(ItemPedido.builder()
                .pedido(pedido)
                .cerveja(cerveja)
                .quantidade(quantidadeCerveja)
                .build());
    }
}

