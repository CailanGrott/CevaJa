package com.fundatec.cevaja.mapper;

import com.fundatec.cevaja.model.Usuario;
import com.fundatec.cevaja.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.model.dto.AdicionaNovoUsuarioOutput;

public class UsuarioMapper {
    public static AdicionaNovoUsuarioInput mapUsuarioToInput(Usuario usuario) {
        return AdicionaNovoUsuarioInput.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .sobrenome(usuario.getSobrenome())
                .dataNascimento(usuario.getDataNascimento())
                .cpf(usuario.getCpf())
                .login(usuario.getLogin())
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
                .login(adicionaNovoUsuarioInput.login())
                .senha(adicionaNovoUsuarioInput.senha())
                .build();
    }

    public static Usuario mapOutputToUsuario(AdicionaNovoUsuarioOutput adicionaNovoUsuarioOutput) {
        return Usuario.builder()
                .nome(adicionaNovoUsuarioOutput.nome())
                .sobrenome(adicionaNovoUsuarioOutput.sobrenome())
                .dataNascimento(adicionaNovoUsuarioOutput.dataNascimento())
                .cpf(adicionaNovoUsuarioOutput.cpf())
                .login(adicionaNovoUsuarioOutput.login())
                .senha(adicionaNovoUsuarioOutput.senha())
                .build();
    }
}
