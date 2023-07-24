package com.fundatec.cevaja.cerveja.controller;

import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.model.dto.EditaTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.service.CervejaService;
import com.fundatec.cevaja.exception.RegraDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Iterable<AdicionaNovoTipoCervejaInput>> buscaTodosTiposCerveja() {
        Iterable<AdicionaNovoTipoCervejaInput> adicionaNovoTipoCervejaInput = cervejaService.buscaTodosTiposCerveja();
        return new ResponseEntity<>(adicionaNovoTipoCervejaInput, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> editaTipoCerveja(@PathVariable("id") Integer id,
                                                 @RequestBody EditaTipoCervejaOutput model) {
        cervejaService.editaTipoCervejaPorId(id, model);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{tipoCerveja}")
    public ResponseEntity<Void> deletaTipoCerveja(@PathVariable("tipoCerveja") String tipoCerveja) {
        cervejaService.deletaTipoCervejaPorId(tipoCerveja);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
