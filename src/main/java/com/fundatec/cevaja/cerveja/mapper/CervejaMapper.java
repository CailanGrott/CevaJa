package com.fundatec.cevaja.cerveja.mapper;

import com.fundatec.cevaja.cerveja.model.Cerveja;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaOutput;

public class CervejaMapper {
    public static AdicionaNovoTipoCervejaInput mapCervejaToInput(Cerveja cerveja) {
        return AdicionaNovoTipoCervejaInput.builder()
                .id(cerveja.getId())
                .tipoCerveja(cerveja.getTipoCerveja())
                .valor(cerveja.getValor())
                .build();
    }

    public static Cerveja mapInputToCerveja(AdicionaNovoTipoCervejaInput input) {
        return Cerveja.builder()
                .id(input.id())
                .tipoCerveja(input.tipoCerveja())
                .valor(input.valor())
                .build();
    }

    public static Cerveja mapOutputToCerveja(AdicionaNovoTipoCervejaOutput output) {
        return Cerveja.builder()
                .tipoCerveja(output.tipoCerveja())
                .valor(output.valor())
                .build();
    }
}
