package com.fundatec.cevaja.pedido.controller;

import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.pedido.model.dto.AdicionaNovoPedido;
import com.fundatec.cevaja.pedido.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/api/v3/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<BigDecimal> addNewOrder(@RequestBody AdicionaNovoPedido novoPedido)  {
        return ResponseEntity.ok(pedidoService.adicionarNovoPedido(novoPedido));
    }
}