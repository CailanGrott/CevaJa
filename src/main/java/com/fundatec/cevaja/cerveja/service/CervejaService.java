package com.fundatec.cevaja.cerveja.service;

import com.fundatec.cevaja.cerveja.mapper.CervejaMapper;
import com.fundatec.cevaja.cerveja.model.Cerveja;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.model.dto.BuscaTodosTiposCerveja;
import com.fundatec.cevaja.cerveja.model.dto.EditaTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.repository.CervejaRepository;
import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.pedido.model.ItemPedido;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static com.fundatec.cevaja.cerveja.mapper.CervejaMapper.mapCervejaToInput;
import static com.fundatec.cevaja.cerveja.mapper.CervejaMapper.mapOutputToCerveja;

@Service
public class CervejaService {
    private final CervejaRepository cervejaRepository;

    public CervejaService(CervejaRepository cervejaRepository) {
        this.cervejaRepository = cervejaRepository;
    }

    public AdicionaNovoTipoCervejaInput adicionaNovoTipoCerveja(AdicionaNovoTipoCervejaOutput adicionaNovoTipoCervejaOutput) throws RegraDeNegocioException {
        if (verificaTipoCervejaExiste(adicionaNovoTipoCervejaOutput.tipoCerveja()))
            throw new RegraDeNegocioException(
                    HttpStatus.CONFLICT,
                    "Tipo de cerveja já cadastrado.",
                    "teste");
        Cerveja cerveja = cervejaRepository.saveAndFlush(mapOutputToCerveja(adicionaNovoTipoCervejaOutput));
        return mapCervejaToInput(cerveja);
    }

    public Collection<BuscaTodosTiposCerveja> buscaTodosTiposCerveja() {
        Collection<BuscaTodosTiposCerveja> buscaTodosTiposCervejas = cervejaRepository.findAll().stream()
                .map(CervejaMapper::mapCervejaToBuscaTodos).toList();
        if (buscaTodosTiposCervejas.isEmpty())
            throw new RegraDeNegocioException(
                    HttpStatus.BAD_REQUEST,
                    "Nenhum tipo de cerveja cadastrado.",
                    "teste");
        return buscaTodosTiposCervejas;
    }

    public Collection<AdicionaNovoTipoCervejaInput> buscaTipoCerveja(Collection<String> tiposCerveja) {
        Collection<AdicionaNovoTipoCervejaInput> buscaTodosTiposCervejas = cervejaRepository.findAll().stream()
                .map(CervejaMapper::mapCervejaToInput)
                .toList();

        if (buscaTodosTiposCervejas.isEmpty())
            throw new RegraDeNegocioException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum tipo de cerveja cadastrado.",
                    "teste");

        return buscaTodosTiposCervejas;
    }

    public void editaTipoCervejaPorId(Integer id, EditaTipoCervejaOutput model) {
        Cerveja editaTipoCerveja = validaAtributos(model, cervejaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de cerveja não encontrado",
                        "teste")));
        cervejaRepository.editaCervejaPorId(model.tipoCerveja(), model.valor(), id);
    }

    public void deletaTipoCervejaPorNome(String tipoCerveja) {
        Cerveja tipoCervejaRetorno = cervejaRepository.buscaTipoCerveja(tipoCerveja)
                .orElseThrow(() -> new RegraDeNegocioException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de cerveja não encontrado",
                        "teste"));
        cervejaRepository.delete(tipoCervejaRetorno);
    }

    public BigDecimal calcularValorTotal(ItemPedido cervejaOutput) {
        Cerveja cerveja = cervejaRepository.findByTipoCerveja(cervejaOutput.getCerveja().getTipoCerveja());

        return BigDecimal.valueOf(cervejaOutput.getQuantidade()).multiply(cerveja.getValor());
    }

    private Boolean verificaTipoCervejaExiste(String tipoCerveja) {
        return cervejaRepository.buscaTipoCerveja(tipoCerveja).isPresent();
    }

    private Cerveja validaAtributos(EditaTipoCervejaOutput model, Cerveja cerveja) {
        Cerveja.builder().tipoCerveja(model.tipoCerveja().isBlank() ? cerveja.getTipoCerveja() : model.tipoCerveja()).valor(model.valor() == null ? cerveja.getValor() : model.valor()).build();
        return cerveja;
    }
}
