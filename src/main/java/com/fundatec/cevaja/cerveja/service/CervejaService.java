package com.fundatec.cevaja.cerveja.service;

import com.fundatec.cevaja.cerveja.mapper.CervejaMapper;
import com.fundatec.cevaja.cerveja.model.Cerveja;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.model.dto.BuscaTodosTiposCerveja;
import com.fundatec.cevaja.cerveja.model.dto.EditaTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.repository.CervejaRepository;
import com.fundatec.cevaja.exception.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static com.fundatec.cevaja.cerveja.mapper.CervejaMapper.mapCervejaToInput;
import static com.fundatec.cevaja.cerveja.mapper.CervejaMapper.mapOutputToCerveja;

@Service
@RequiredArgsConstructor
public class CervejaService {
    private final CervejaRepository cervejaRepository;

    /**
     * Adiciona um novo tipo de cerveja no sistema.
     *
     * @param adicionaNovoTipoCervejaOutput As informações do novo tipo de cerveja a ser adicionado.
     * @return AdicionaNovoTipoCervejaInput com as informações do tipo de cerveja recém-adicionado.
     * @throws RegraDeNegocioException Caso ocorra um erro na execução da operação, como tipo de cerveja já cadastrado.
     */

    public AdicionaNovoTipoCervejaInput adicionaNovoTipoCerveja(AdicionaNovoTipoCervejaOutput adicionaNovoTipoCervejaOutput) throws RegraDeNegocioException {
        if (verificaTipoCervejaExiste(adicionaNovoTipoCervejaOutput.tipoCerveja()))
            throw new RegraDeNegocioException(
                    HttpStatus.CONFLICT,
                    "Tipo de cerveja já cadastrado",
                    "Este tipo de cerveja já existe no sistema, tente outro."
            );
        Cerveja cerveja = cervejaRepository.saveAndFlush(mapOutputToCerveja(adicionaNovoTipoCervejaOutput));
        return mapCervejaToInput(cerveja);
    }

    /**
     * Busca todos os tipos de cerveja cadastrados no sistema.
     *
     * @return Collection<BuscaTodosTiposCerveja> com as informações de todos os tipos de cerveja cadastrados.
     * @throws RegraDeNegocioException Caso não haja tipos de cerveja cadastrados no sistema.
     */

    public Collection<BuscaTodosTiposCerveja> buscaTodosTiposCerveja() {
        Collection<BuscaTodosTiposCerveja> buscaTodosTiposCervejas = cervejaRepository.findAll().stream()
                .map(CervejaMapper::mapCervejaToBuscaTodos).toList();
        if (buscaTodosTiposCervejas.isEmpty())
            throw new RegraDeNegocioException(
                    HttpStatus.BAD_REQUEST,
                    "Nenhum tipo de cerveja cadastrado",
                    "A lista de tipos de cerveja cadastrados no sistema está vazia, tente cadastrar alguns."
            );
        return buscaTodosTiposCervejas;
    }

    /**
     * Busca os tipos de cerveja correspondentes aos nomes informados.
     *
     * @param tiposCerveja Os nomes dos tipos de cerveja a serem buscados.
     * @return Collection<AdicionaNovoTipoCervejaInput> com as informações dos tipos de cerveja encontrados.
     * @throws RegraDeNegocioException Caso não haja tipos de cerveja cadastrados no sistema.
     */

    public Collection<AdicionaNovoTipoCervejaInput> buscaTipoCerveja(Collection<String> tiposCerveja) {
        Collection<AdicionaNovoTipoCervejaInput> buscaTodosTiposCervejas = cervejaRepository.findAll().stream()
                .map(CervejaMapper::mapCervejaToInput)
                .toList();

        if (buscaTodosTiposCervejas.isEmpty())
            throw new RegraDeNegocioException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum tipo de cerveja cadastrado",
                    "A lista de tipos de cerveja cadastrados no sistema está vazia, tente cadastrar alguns."
            );
        return buscaTodosTiposCervejas;
    }

    /**
     * Edita as informações de um tipo de cerveja existente no sistema, identificado pelo seu ID.
     *
     * @param id    O ID do tipo de cerveja a ser editado.
     * @param model As novas informações a serem atualizadas no tipo de cerveja.
     */

    public void editaTipoCervejaPorId(Integer id, EditaTipoCervejaOutput model) {
        var editaTipoCerveja = validaAtributos(model, cervejaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de cerveja não encontrado",
                        "Não encontramos esse tipo de cerveja."
                )));
        cervejaRepository.editaCervejaPorId(model.tipoCerveja(), model.valor(), id);
    }

    /**
     * Deleta um tipo de cerveja existente no sistema, identificado pelo seu nome.
     *
     * @param tipoCerveja O nome do tipo de cerveja a ser excluído.
     */

    public void deletaTipoCervejaPorNome(String tipoCerveja) {
        Cerveja tipoCervejaRetorno = cervejaRepository.buscaTipoCerveja(tipoCerveja)
                .orElseThrow(() -> new RegraDeNegocioException(
                        HttpStatus.NOT_FOUND,
                        "Tipo de cerveja não encontrado",
                        "Não encontramos esse tipo de cerveja."));
        cervejaRepository.delete(tipoCervejaRetorno);
    }

    /**
     * Verifica se o tipo de cerveja ja existe.
     *
     */

    private Boolean verificaTipoCervejaExiste(String tipoCerveja) {
        return cervejaRepository.buscaTipoCerveja(tipoCerveja).isPresent();
    }

    /**
     * Valida os atributos.
     *
     */

    private Cerveja validaAtributos(EditaTipoCervejaOutput model, Cerveja cerveja) {
        Cerveja.builder().tipoCerveja(model.tipoCerveja().isBlank() ? cerveja.getTipoCerveja() : model.tipoCerveja()).valor(model.valor() == null ? cerveja.getValor() : model.valor()).build();
        return cerveja;
    }
}
