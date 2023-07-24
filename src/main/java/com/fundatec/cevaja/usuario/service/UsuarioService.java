package com.fundatec.cevaja.usuario.service;

import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.usuario.mapper.UsuarioMapper;
import com.fundatec.cevaja.usuario.model.Usuario;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioOutput;
import com.fundatec.cevaja.usuario.model.dto.BuscaTodosUsuarios;
import com.fundatec.cevaja.usuario.model.dto.EditaUsuarioOutput;
import com.fundatec.cevaja.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.fundatec.cevaja.usuario.mapper.UsuarioMapper.mapOutputToUsuario;
import static com.fundatec.cevaja.usuario.mapper.UsuarioMapper.mapUsuarioToInput;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public AdicionaNovoUsuarioInput adicionaNovoUsuario(AdicionaNovoUsuarioOutput adicionaNovoUsuarioOutput) throws RegraDeNegocioException {
        if (buscarPorUsername(adicionaNovoUsuarioOutput.username())) {
            throw new RegraDeNegocioException("Usuário já cadastrado");
        }

        Long idade = ChronoUnit.YEARS.between(adicionaNovoUsuarioOutput.dataNascimento(), LocalDate.now());
        if (idade < 18) {
            throw new RegraDeNegocioException("Não é possível adicionar um pedido para um Usuário menor de idade!");
        }

        Usuario usuario = usuarioRepository.saveAndFlush(mapOutputToUsuario(adicionaNovoUsuarioOutput));
        return mapUsuarioToInput(usuario);
    }

    public Iterable<BuscaTodosUsuarios> buscaTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioMapper::mapUsuarioToBuscaTodos)
                .toList();
    }

    public Usuario buscaUsuarioPorUsername(String username) {
        return usuarioRepository.buscaUsuarioPorUsername(username).get();
    }

    public void editaUsuarioPorId(Integer id, EditaUsuarioOutput model) {
        var editaUsuario = validaAtributos(model, usuarioRepository.findById(id).orElseThrow());
        usuarioRepository.editaUsuarioById(editaUsuario.getNome(),
                editaUsuario.getSobrenome(), id);
    }

    public void deletaUsuarioPorId(Integer id) {
        var usuarioRetorno = usuarioRepository.findById(id).orElseThrow();
        usuarioRepository.delete(usuarioRetorno);
    }

    private Boolean buscarPorUsername(String username) {
        return usuarioRepository.buscaUsuarioPorUsername(username).isPresent();
    }

    private Usuario validaAtributos(EditaUsuarioOutput model, Usuario usuario) {
        return Usuario.builder()
                .nome(model.nome().isBlank() ? usuario.getNome() : model.nome())
                .sobrenome(model.sobrenome().isBlank() ? usuario.getSobrenome() : model.sobrenome())
                .build();
    }
}
