package com.fundatec.cevaja.pedido.service;

import com.fundatec.cevaja.cerveja.service.CervejaService;
import com.fundatec.cevaja.pedido.model.dto.AdicionaNovoPedidoInput;
import com.fundatec.cevaja.pedido.model.dto.AdicionaNovoPedidoOutput;
import com.fundatec.cevaja.pedido.model.dto.AdicionaUsuarioToPedido;
import com.fundatec.cevaja.pedido.repository.PedidoRepository;
import com.fundatec.cevaja.usuario.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final UsuarioService usuarioService;
    private final CervejaService cervejaService;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioService usuarioService, CervejaService cervejaService) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioService = usuarioService;
        this.cervejaService = cervejaService;
    }

//    public AdicionaNovoPedidoInput criarPedido(AdicionaNovoPedidoOutput output) {
//        List<BigDecimal> valores = new ArrayList<>();
//        AdicionaUsuarioToPedido
//    }
}
