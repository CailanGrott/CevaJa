package com.fundatec.cevaja.cerveja.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundatec.cevaja.pedido.model.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cerveja")
public class Cerveja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cerveja", nullable = false)
    private Integer id;

    @Column(name = "tipo_cerveja", nullable = false)
    private String tipoCerveja;

    @Column(name = "valor",nullable = false)
    private BigDecimal valor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido")
    private Pedido pedido;
}
