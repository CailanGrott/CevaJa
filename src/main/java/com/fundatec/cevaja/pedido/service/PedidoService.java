package com.fundatec.cevaja.pedido.service;

import com.fundatec.cevaja.cerveja.service.CervejaService;
import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.pedido.model.ItemPedido;
import com.fundatec.cevaja.pedido.model.Pedido;
import com.fundatec.cevaja.pedido.model.dto.AdicionaNovoPedido;
import com.fundatec.cevaja.pedido.repository.PedidoRepository;
import com.fundatec.cevaja.usuario.service.UsuarioService;
import com.fundatec.cevaja.weather.integration.response.CurrentResponse;
import com.fundatec.cevaja.weather.integration.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.fundatec.cevaja.cerveja.mapper.CervejaMapper.mapToCerveja;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoService itemPedidoService;
    private final WeatherService weatherService;
    private final UsuarioService usuarioService;
    private final CervejaService cervejaService;

    public void adicionarNovoPedido(AdicionaNovoPedido novoPedido) throws Exception {

        var usuario = usuarioService.buscaUsuarioPorUsername(novoPedido.username());
        var cervejas = cervejaService.buscaTodosTiposCerveja(buscaNomeTipoCerveja(novoPedido));

        var pedido = pedidoRepository.saveAndFlush(Pedido.builder()
                .usuario(usuario)
                .build());

        cervejas.forEach(cerveja ->
                itemPedidoService.associarPedidosAoProduto(
                        mapToCerveja(cerveja),
                        buscaQuantidadeDeCervejas(novoPedido, cerveja.tipoCerveja()),
                        pedido));


    }

    public BigDecimal descontoPorCompraDeCervejas(ItemPedido cervejaCreatePedidoDto) {
        BigDecimal valorTotal = cervejaService.calcularValorTotal(cervejaCreatePedidoDto);

        if (cervejaCreatePedidoDto.getQuantidade() > 10) {
            BigDecimal valorTemporario = valorTotal.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(10));
            valorTotal = valorTotal.subtract(valorTemporario);
        }
        return valorTotal;
    }

    public BigDecimal descontoPorBaixoClima (BigDecimal valor) {
        CurrentResponse currentResponse = weatherService.buscarTemp().getCurrent();
        if (currentResponse.getTemp_c() <= 22) {
            valor = valor.subtract(valor.divide(new BigDecimal(100)).multiply(new BigDecimal(15)));
        }
        return valor;
    }

    private Integer buscaQuantidadeDeCervejas(AdicionaNovoPedido pedido, String tipoCerveja) {
        return pedido.cervejas().stream()
                .filter(product -> product.tipoCerveja().equals(tipoCerveja))
                .map(AdicionaNovoPedido.Cerveja::quantidade)
                .findFirst()
                .orElse(1);
    }

    private List<String> buscaNomeTipoCerveja(AdicionaNovoPedido novoPedido) {
        return novoPedido.cervejas().stream()
                .map(AdicionaNovoPedido.Cerveja::tipoCerveja)
                .toList();
    }
}