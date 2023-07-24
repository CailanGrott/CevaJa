package com.fundatec.cevaja.cerveja.model;

import com.fundatec.cevaja.pedido.model.ItemPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cerveja", schema = "cevaja")
public class Cerveja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cerveja", nullable = false)
    private Integer id;

    @Column(name = "tipo_cerveja", nullable = false)
    private String tipoCerveja;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @OneToMany(mappedBy = "cerveja", cascade = CascadeType.ALL)
    private Set<ItemPedido> itensPedido = new LinkedHashSet<>();
}
