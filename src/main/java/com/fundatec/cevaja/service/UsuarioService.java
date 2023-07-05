package com.fundatec.cevaja.service;

import com.fundatec.cevaja.mapper.UsuarioMapper;
import com.fundatec.cevaja.model.Usuario;
import com.fundatec.cevaja.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.model.dto.AdicionaNovoUsuarioOutput;
import com.fundatec.cevaja.model.dto.EditaUsuarioOutput;
import com.fundatec.cevaja.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import static com.fundatec.cevaja.mapper.UsuarioMapper.*;

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
