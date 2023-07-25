package com.fundatec.cevaja.cerveja.controller;

import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.model.dto.BuscaTodosTiposCerveja;
import com.fundatec.cevaja.cerveja.model.dto.EditaTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.service.CervejaService;
import com.fundatec.cevaja.exception.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/cervejas/tipos")
public class CervejaController {
    public final CervejaService cervejaService;

    /**
     * Endpoint para adicionar um novo tipo de cerveja no sistema.
     *
     * @param adicionaNovoTipoCervejaOutput As informações do novo tipo de cerveja a ser adicionado.
     * @return ResponseEntity com status 201 (CREATED) e o objeto AdicionaNovoTipoCervejaInput representando o tipo de cerveja recém-adicionado.
     * @throws RegraDeNegocioException Caso ocorra um erro na execução da operação.
     */

    @PostMapping
    public ResponseEntity<AdicionaNovoTipoCervejaInput> adicionaNovoTipoCerveja(@RequestBody AdicionaNovoTipoCervejaOutput adicionaNovoTipoCervejaOutput) throws RegraDeNegocioException {
        AdicionaNovoTipoCervejaInput adicionaNovoTipoCervejaInput = cervejaService.adicionaNovoTipoCerveja(adicionaNovoTipoCervejaOutput);
        return new ResponseEntity<>(adicionaNovoTipoCervejaInput, HttpStatus.CREATED);
    }

    /**
     * Endpoint para buscar todos os tipos de cerveja cadastrados no sistema.
     *
     * @return ResponseEntity com status 200 (OK) e uma coleção de objetos BuscaTodosTiposCerveja representando os tipos de cerveja cadastrados.
     * @throws RegraDeNegocioException Caso ocorra um erro na execução da operação.
     */

    @GetMapping
    public ResponseEntity<Collection<BuscaTodosTiposCerveja>> buscaTodosTiposCerveja() {
        Collection<BuscaTodosTiposCerveja> buscaTodosTiposCervejas = cervejaService.buscaTodosTiposCerveja();
        return new ResponseEntity<>(buscaTodosTiposCervejas, HttpStatus.OK);
    }

    /**
     * Endpoint para editar as informações de um tipo de cerveja existente no sistema, identificado pelo seu ID.
     *
     * @param id    O ID do tipo de cerveja a ser editado.
     * @param model As novas informações a serem atualizadas no tipo de cerveja.
     * @return ResponseEntity com status 204 (NO CONTENT) em caso de sucesso.
     * @throws RegraDeNegocioException Caso ocorra um erro na execução da operação.
     */

    @PutMapping("{id}")
    public ResponseEntity<Void> editaTipoCerveja(@PathVariable("id") Integer id,
                                                 @RequestBody EditaTipoCervejaOutput model) throws RegraDeNegocioException {
        cervejaService.editaTipoCervejaPorId(id, model);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para deletar um tipo de cerveja existente no sistema, identificado pelo seu nome.
     *
     * @param tipoCerveja O nome do tipo de cerveja a ser excluído.
     * @return ResponseEntity com status 202 (ACCEPTED) em caso de sucesso.
     * @throws RegraDeNegocioException Caso ocorra um erro na execução da operação.
     */

    @DeleteMapping("{tipoCerveja}")
    public ResponseEntity<Void> deletaTipoCerveja(@PathVariable("tipoCerveja") String tipoCerveja) throws RegraDeNegocioException {
        cervejaService.deletaTipoCervejaPorNome(tipoCerveja);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
