package com.fundatec.cevaja.pedido.service;

import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.service.CervejaService;
import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.pedido.model.Pedido;
import com.fundatec.cevaja.pedido.model.dto.AdicionaNovoPedido;
import com.fundatec.cevaja.pedido.repository.PedidoRepository;
import com.fundatec.cevaja.usuario.model.Usuario;
import com.fundatec.cevaja.usuario.service.UsuarioService;
import com.fundatec.cevaja.weather.integration.response.CurrentResponse;
import com.fundatec.cevaja.weather.integration.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static com.fundatec.cevaja.cerveja.mapper.CervejaMapper.mapToCerveja;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoService itemPedidoService;
    private final WeatherService weatherService;
    private final UsuarioService usuarioService;
    private final CervejaService cervejaService;

    public BigDecimal adicionarNovoPedido(AdicionaNovoPedido novoPedido) {
        Usuario usuario = usuarioService.buscaUsuarioPorUsername(novoPedido.username());
        Collection<AdicionaNovoTipoCervejaInput> cervejas = cervejaService.buscaTipoCerveja(buscaNomeTipoCerveja(novoPedido));
        Pedido pedido = pedidoRepository.saveAndFlush(Pedido.builder()
                .usuario(usuario)
                .build());

        cervejas.forEach(cerveja ->
                itemPedidoService.associarPedidosAoProduto(mapToCerveja(cerveja),
                        buscaQuantidadeDeCervejas(novoPedido, cerveja.tipoCerveja()), pedido));

        return calcularValorTotalPedido(novoPedido, cervejas);
    }

    public BigDecimal calcularValorTotalPedido(AdicionaNovoPedido novoPedido,
                                               Collection<AdicionaNovoTipoCervejaInput> cervejas) {
        return cervejas.stream()
                .flatMap(cerveja -> filtrarPorTipoCervejaParaRealizarDescontoPorUnidade(novoPedido, cerveja))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Stream<BigDecimal> filtrarPorTipoCervejaParaRealizarDescontoPorUnidade(AdicionaNovoPedido novoPedido,
                                                                                   AdicionaNovoTipoCervejaInput cerveja) {
        return novoPedido.cervejas().stream()
                .filter(cervejaInformada -> compararTipoCerveja(cerveja, cervejaInformada))
                .map(calculoValorTotal -> aplicarDescontoSeNecessario(cerveja, calculoValorTotal));
    }

    private static boolean compararTipoCerveja(AdicionaNovoTipoCervejaInput cerveja, AdicionaNovoPedido.Cerveja cervejaInformada) {
        return cervejaInformada.tipoCerveja().equals(cerveja.tipoCerveja());
    }

    private BigDecimal aplicarDescontoSeNecessario(AdicionaNovoTipoCervejaInput cerveja,
                                                   AdicionaNovoPedido.Cerveja calculoValorTotal) {
        BigDecimal valorTotal = cerveja.valor().multiply(BigDecimal.valueOf(calculoValorTotal.quantidade()));
        BigDecimal descontoPorMaisDeDezUnidades = valorTotal.multiply(BigDecimal.valueOf(0.1));
        BigDecimal descontoPorBaixoClima = valorTotal.multiply(BigDecimal.valueOf(0.15));
        CurrentResponse currentResponse = weatherService.buscarTemp().getCurrent();
        if (currentResponse.getTemp_c() <= 22)
            return valorTotal.subtract(descontoPorBaixoClima);
        if (calculoValorTotal.quantidade() >= 10) {
            return valorTotal.subtract(descontoPorMaisDeDezUnidades);
        }
        return valorTotal;
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