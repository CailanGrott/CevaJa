package com.fundatec.cevaja.usuario.service;

import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.usuario.mapper.UsuarioMapper;
import com.fundatec.cevaja.usuario.model.Usuario;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioOutput;
import com.fundatec.cevaja.usuario.model.dto.BuscaTodosUsuarios;
import com.fundatec.cevaja.usuario.model.dto.EditaUsuarioOutput;
import com.fundatec.cevaja.usuario.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import static com.fundatec.cevaja.usuario.mapper.UsuarioMapper.mapOutputToUsuario;
import static com.fundatec.cevaja.usuario.mapper.UsuarioMapper.mapUsuarioToInput;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public AdicionaNovoUsuarioInput adicionaNovoUsuario(AdicionaNovoUsuarioOutput adicionaNovoUsuarioOutput) throws RegraDeNegocioException {
        if (verificaUsuarioExiste(adicionaNovoUsuarioOutput.username())) {
            throw new RegraDeNegocioException(
                    HttpStatus.CONFLICT,
                    "Tipo de cerveja já cadastrado.",
                    "teste");
        }

        Long idade = ChronoUnit.YEARS.between(adicionaNovoUsuarioOutput.dataNascimento(), LocalDate.now());
        if (idade < 18) {
            throw new RegraDeNegocioException(HttpStatus.FORBIDDEN,
                    "Não é possível adicionar um pedido para um Usuário menor de idade!",
                    "teste");
        }

        Usuario usuario = usuarioRepository.saveAndFlush(mapOutputToUsuario(adicionaNovoUsuarioOutput));
        return mapUsuarioToInput(usuario);
    }

    public Collection<BuscaTodosUsuarios> buscaTodosUsuarios() throws RegraDeNegocioException {
        Collection<BuscaTodosUsuarios> buscaTodosUsuarios = usuarioRepository.findAll().stream()
                .map(UsuarioMapper::mapUsuarioToBuscaTodos)
                .toList();

        if (buscaTodosUsuarios.isEmpty()) {
            throw new RegraDeNegocioException(HttpStatus.NOT_FOUND, "Sem usuarios cadastrados", "teste");
        }

        return buscaTodosUsuarios;
    }

    public Usuario buscaUsuarioPorUsername(String username) throws RegraDeNegocioException {
        return usuarioRepository.buscaUsuarioPorUsername(username)
                .orElseThrow(() -> new RegraDeNegocioException(HttpStatus.NOT_FOUND, "Usuário não encontrado", "teste"));
    }

    public void editaUsuarioPorId(Integer id, EditaUsuarioOutput model) throws RegraDeNegocioException {
        Usuario editaUsuario = validaAtributos(model, usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException(HttpStatus.NOT_FOUND, "Usuário não encontrado", "teste")));
        usuarioRepository.editaUsuarioById(editaUsuario.getNome(),
                editaUsuario.getSobrenome(), id);
    }

    public void deletaUsuarioPorUsername(String username) throws RegraDeNegocioException {
        Usuario usuario = usuarioRepository.buscaUsuarioPorUsername(username)
                .orElseThrow(() -> new RegraDeNegocioException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado",
                        "teste"));
        usuarioRepository.delete(usuario);
    }

    private Boolean verificaUsuarioExiste(String username) {
        return usuarioRepository.buscaUsuarioPorUsername(username).isPresent();
    }

    private Usuario validaAtributos(EditaUsuarioOutput model, Usuario usuario) {
        return Usuario.builder()
                .nome(model.nome().isBlank() ? usuario.getNome() : model.nome())
                .sobrenome(model.sobrenome().isBlank() ? usuario.getSobrenome() : model.sobrenome())
                .build();
    }
}
