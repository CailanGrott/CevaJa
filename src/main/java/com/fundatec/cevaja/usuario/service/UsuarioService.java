package com.fundatec.cevaja.usuario.service;

import com.fundatec.cevaja.usuario.mapper.UsuarioMapper;
import com.fundatec.cevaja.usuario.model.Usuario;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioOutput;
import com.fundatec.cevaja.usuario.model.dto.EditaUsuarioOutput;
import com.fundatec.cevaja.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import static com.fundatec.cevaja.usuario.mapper.UsuarioMapper.*;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public AdicionaNovoUsuarioInput adicionaNovoUsuario(AdicionaNovoUsuarioOutput adicionaNovoUsuarioOutput) {
        Usuario usuario = usuarioRepository.saveAndFlush(mapOutputToUsuario(adicionaNovoUsuarioOutput));
        return mapUsuarioToInput(usuario);
    }

    public Iterable<AdicionaNovoUsuarioInput> buscaTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::mapUsuarioToInput)
                .toList();
    }

    public void editaUsuarioPorId(Integer id, EditaUsuarioOutput model) {
        var editaUsuario = validaAtributos(model, usuarioRepository.findById(id).orElseThrow());
        usuarioRepository.editaUsuarioById(editaUsuario.getNome(),
                editaUsuario.getSobrenome(), id);
    }

    public void deletaUsuarioPorId(Integer id){
        var usuarioRetorno = usuarioRepository.findById(id).orElseThrow();
        usuarioRepository.delete(usuarioRetorno);
    }

    private Usuario validaAtributos(EditaUsuarioOutput model, Usuario usuario) {
        return Usuario.builder()
                .nome(model.nome().isBlank() ? usuario.getNome() : model.nome())
                .sobrenome(model.sobrenome().isBlank() ? usuario.getSobrenome() : model.sobrenome())
                .build();
    }
}
