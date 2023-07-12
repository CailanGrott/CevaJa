package com.fundatec.cevaja.pedido.model;

import com.fundatec.cevaja.cerveja.model.Cerveja;
import com.fundatec.cevaja.pedido.model.enums.TipoPagamento;
import com.fundatec.cevaja.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido")
    private Set<Cerveja> cervejas = new LinkedHashSet<>();

    @Column
    private BigDecimal valorFinal;

    @Column
    private TipoPagamento tipoPagamento;
}