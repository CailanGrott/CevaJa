package com.fundatec.cevaja.pedido.controller;

import com.fundatec.cevaja.pedido.model.dto.AdicionaNovoPedido;
import com.fundatec.cevaja.pedido.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    /**
     * Endpoint para adicionar um novo pedido no sistema.
     *
     * @param novoPedido As informações do novo pedido a ser adicionado.
     * @return ResponseEntity com status 200 (OK) e o valor total do pedido após a adição.
     */

    @PostMapping
    public ResponseEntity<BigDecimal> addNewOrder(@RequestBody AdicionaNovoPedido novoPedido)  {
        return ResponseEntity.ok(pedidoService.adicionarNovoPedido(novoPedido));
    }
}