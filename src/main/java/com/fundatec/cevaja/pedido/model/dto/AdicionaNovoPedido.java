package com.fundatec.cevaja.pedido.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.Collection;

public record AdicionaNovoPedido(
        @NotBlank(message = "Preencha o campo username para fazer o pedido")
        @Schema(description = "Username do usuário para fazer o pedido", example = "cailaNgtR")
        String username,
        Collection<Cerveja> cervejas) {
    public record Cerveja(
            @NotBlank(message = "Preencha o campo tipo cerveja para fazer o pedido")
            @Schema(description = "Escolha o tipo de cerveja que irá pedir", example = "IPA")
            String tipoCerveja,
            @NotBlank(message = "Preencha o campo quantidade para fazer o pedido")
            @Schema(description = "O campo quantidade se refere ao número de cervejas que irá pedir", example = "8")
            Integer quantidade) {
    }
}
