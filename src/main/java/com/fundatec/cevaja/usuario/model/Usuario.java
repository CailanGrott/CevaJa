package com.fundatec.cevaja.usuario.model;

import com.fundatec.cevaja.pedido.model.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "senha", nullable = false)
    private String senha;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<Pedido> pedidos;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_login")
    private Login login;
}
