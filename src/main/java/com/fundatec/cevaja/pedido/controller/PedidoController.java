package com.fundatec.cevaja.pedido.controller;

import com.fundatec.cevaja.pedido.model.Pedido;
import com.fundatec.cevaja.pedido.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

//    @PostMapping
//    public ResponseEntity<Pedido> criarPedido(Pedido pedido) {
//        return new ResponseEntity<>(pedidoService.criarPedido(pedido), HttpStatus.OK);
//    }
}
