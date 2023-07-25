package com.fundatec.cevaja.usuario.mapper;

import com.fundatec.cevaja.usuario.model.Usuario;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioOutput;
import com.fundatec.cevaja.usuario.model.dto.BuscaTodosUsuarios;

public class UsuarioMapper {
    public static AdicionaNovoUsuarioInput mapUsuarioToInput(Usuario usuario) {
        return AdicionaNovoUsuarioInput.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .dataNascimento(usuario.getDataNascimento())
                .cpf(usuario.getCpf())
                .username(usuario.getUsername())
                .senha(usuario.getSenha())
                .build();
    }

    public static Usuario mapInputToUsuario(AdicionaNovoUsuarioInput adicionaNovoUsuarioInput) {
        return Usuario.builder()
                .id(adicionaNovoUsuarioInput.id())
                .nome(adicionaNovoUsuarioInput.nome())
                .sobrenome(adicionaNovoUsuarioInput.sobrenome())
                .dataNascimento(adicionaNovoUsuarioInput.dataNascimento())
                .cpf(adicionaNovoUsuarioInput.cpf())
                .username(adicionaNovoUsuarioInput.username())
                .senha(adicionaNovoUsuarioInput.senha())
                .build();
    }

    public static Usuario mapOutputToUsuario(AdicionaNovoUsuarioOutput adicionaNovoUsuarioOutput) {
        return Usuario.builder()
                .nome(adicionaNovoUsuarioOutput.nome())
                .sobrenome(adicionaNovoUsuarioOutput.sobrenome())
                .dataNascimento(adicionaNovoUsuarioOutput.dataNascimento())
                .cpf(adicionaNovoUsuarioOutput.cpf())
                .username(adicionaNovoUsuarioOutput.username())
                .senha(adicionaNovoUsuarioOutput.senha())
                .build();
    }

    public static BuscaTodosUsuarios mapUsuarioToBuscaTodos(Usuario usuario) {
        return BuscaTodosUsuarios.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .dataNascimento(usuario.getDataNascimento())
                .cpf(usuario.getCpf())
                .username(usuario.getUsername())
                .build();
    }
}
