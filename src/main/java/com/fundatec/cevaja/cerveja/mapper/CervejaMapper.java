package com.fundatec.cevaja.cerveja.mapper;

import com.fundatec.cevaja.cerveja.model.Cerveja;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.model.dto.BuscaTodosTiposCerveja;

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

    public static Cerveja mapToCerveja(AdicionaNovoTipoCervejaInput cerveja) {
        return Cerveja.builder()
                .id(cerveja.id())
                .tipoCerveja(cerveja.tipoCerveja())
                .valor(cerveja.valor())
                .build();
    }

    public static BuscaTodosTiposCerveja mapCervejaToBuscaTodos(Cerveja cerveja) {
        return BuscaTodosTiposCerveja.builder()
                .id(cerveja.getId())
                .tipoCerveja(cerveja.getTipoCerveja())
                .valor(cerveja.getValor())
                .build();
    }
}
