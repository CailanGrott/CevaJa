package com.fundatec.cevaja.cerveja.controller;

import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.model.dto.BuscaTodosTiposCerveja;
import com.fundatec.cevaja.cerveja.model.dto.EditaTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.service.CervejaService;
import com.fundatec.cevaja.exception.RegraDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v2/cervejas/tipos")
public class CervejaController {
    public final CervejaService cervejaService;

    public CervejaController(CervejaService cervejaService) {
        this.cervejaService = cervejaService;
    }

    @PostMapping
    public ResponseEntity<AdicionaNovoTipoCervejaInput> adicionaNovoTipoCerveja(@RequestBody AdicionaNovoTipoCervejaOutput adicionaNovoTipoCervejaOutput) throws RegraDeNegocioException {
        AdicionaNovoTipoCervejaInput adicionaNovoTipoCervejaInput = cervejaService.adicionaNovoTipoCerveja(adicionaNovoTipoCervejaOutput);
        return new ResponseEntity<>(adicionaNovoTipoCervejaInput, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<BuscaTodosTiposCerveja>> buscaTodosTiposCerveja() {
        Collection<BuscaTodosTiposCerveja> buscaTodosTiposCervejas = cervejaService.buscaTodosTiposCerveja();
        return new ResponseEntity<>(buscaTodosTiposCervejas, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> editaTipoCerveja(@PathVariable("id") Integer id,
                                                 @RequestBody EditaTipoCervejaOutput model) throws RegraDeNegocioException {
        cervejaService.editaTipoCervejaPorId(id, model);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{tipoCerveja}")
    public ResponseEntity<Void> deletaTipoCerveja(@PathVariable("tipoCerveja") String tipoCerveja) throws RegraDeNegocioException {
        cervejaService.deletaTipoCervejaPorNome(tipoCerveja);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
