package com.fundatec.cevaja.cerveja.service;

import com.fundatec.cevaja.cerveja.mapper.CervejaMapper;
import com.fundatec.cevaja.cerveja.model.Cerveja;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.model.dto.EditaTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.repository.CervejaRepository;
import org.springframework.stereotype.Service;

import static com.fundatec.cevaja.cerveja.mapper.CervejaMapper.*;

@Service
public class CervejaService {
    private final CervejaRepository cervejaRepository;

    public CervejaService(CervejaRepository cervejaRepository) {
        this.cervejaRepository = cervejaRepository;
    }

    public AdicionaNovoTipoCervejaInput adicionaNovoTipoCerveja(AdicionaNovoTipoCervejaOutput adicionaNovoTipoCervejaOutput) {
        Cerveja cerveja = cervejaRepository.saveAndFlush(mapOutputToCerveja(adicionaNovoTipoCervejaOutput));
        return mapCervejaToInput(cerveja);
    }

    public Iterable<AdicionaNovoTipoCervejaInput> buscaTodosTiposCerveja() {
        return cervejaRepository.findAll().stream()
                .map(CervejaMapper::mapCervejaToInput)
                .toList();
    }

    public void editaTipoCervejaPorId(Integer id, EditaTipoCervejaOutput model) {
        var editaTipoCerveja = validaAtributos(model, cervejaRepository.findById(id).orElseThrow());
        cervejaRepository.editaCervejaPorId(model.tipoCerveja(),
                model.valor(), id);
    }

    public void deletaTipoCervejaPorId(Integer id) {
        var tipoCervejaRetorno = cervejaRepository.findById(id).orElseThrow();
        cervejaRepository.delete(tipoCervejaRetorno);
    }

    private Cerveja validaAtributos(EditaTipoCervejaOutput model, Cerveja cerveja) {
        return Cerveja.builder()
                .tipoCerveja(model.tipoCerveja().isBlank() ? cerveja.getTipoCerveja() : model.tipoCerveja())
                .valor(model.valor() == null ? cerveja.getValor() : model.valor())
                .build();
    }
}
