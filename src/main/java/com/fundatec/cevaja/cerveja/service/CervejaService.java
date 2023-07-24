package com.fundatec.cevaja.cerveja.service;

import com.fundatec.cevaja.cerveja.mapper.CervejaMapper;
import com.fundatec.cevaja.cerveja.model.Cerveja;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.model.dto.EditaTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.repository.CervejaRepository;
import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.pedido.model.ItemPedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

import static com.fundatec.cevaja.cerveja.mapper.CervejaMapper.*;

@Service
public class CervejaService {
    private final CervejaRepository cervejaRepository;

    public CervejaService(CervejaRepository cervejaRepository) {
        this.cervejaRepository = cervejaRepository;
    }

    public AdicionaNovoTipoCervejaInput adicionaNovoTipoCerveja(AdicionaNovoTipoCervejaOutput adicionaNovoTipoCervejaOutput) throws RegraDeNegocioException {
        Cerveja cerveja = cervejaRepository.saveAndFlush(mapOutputToCerveja(adicionaNovoTipoCervejaOutput));
        return mapCervejaToInput(cerveja);
    }

    public Iterable<AdicionaNovoTipoCervejaInput> buscaTodosTiposCerveja() {
        return cervejaRepository.findAll().stream()
                .map(CervejaMapper::mapCervejaToInput)
                .toList();
    }

    public Iterable<AdicionaNovoTipoCervejaInput> buscaTodosTiposCerveja(Collection<String> tiposCerveja) {
        return cervejaRepository.findByTipoCervejaIn(tiposCerveja).stream()
                .map(CervejaMapper::mapCervejaToInput)
                .toList();
    }

    public void editaTipoCervejaPorId(Integer id, EditaTipoCervejaOutput model) {
        validaAtributos(model, cervejaRepository.findById(id).orElseThrow());
        cervejaRepository.editaCervejaPorId(model.tipoCerveja(),
                model.valor(), id);
    }

    public void deletaTipoCervejaPorId(String tipoCerveja) {
        Cerveja tipoCervejaRetorno = cervejaRepository.findByTipoCerveja(tipoCerveja);
        cervejaRepository.delete(tipoCervejaRetorno);
    }

    public BigDecimal calcularValorTotal(ItemPedido cervejaOutput) {
        Cerveja cerveja = cervejaRepository.findByTipoCerveja(cervejaOutput.getCerveja().getTipoCerveja());

        return BigDecimal.valueOf(cervejaOutput.getQuantidade())
                .multiply(cerveja.getValor());
    }

    private void validaAtributos(EditaTipoCervejaOutput model, Cerveja cerveja) {
        Cerveja.builder()
                .tipoCerveja(model.tipoCerveja().isBlank() ? cerveja.getTipoCerveja() : model.tipoCerveja())
                .valor(model.valor() == null ? cerveja.getValor() : model.valor())
                .build();
    }
}
