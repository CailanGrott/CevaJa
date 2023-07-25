package com.fundatec.cevaja.usuario.service;

import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.usuario.mapper.UsuarioMapper;
import com.fundatec.cevaja.usuario.model.Usuario;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioOutput;
import com.fundatec.cevaja.usuario.model.dto.BuscaTodosUsuarios;
import com.fundatec.cevaja.usuario.model.dto.EditaUsuarioOutput;
import com.fundatec.cevaja.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import static com.fundatec.cevaja.usuario.mapper.UsuarioMapper.mapOutputToUsuario;
import static com.fundatec.cevaja.usuario.mapper.UsuarioMapper.mapUsuarioToInput;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    /**
     * Adiciona um novo usuário no sistema.
     *
     * @param adicionaNovoUsuarioOutput As informações do novo usuário a ser adicionado.
     * @return AdicionaNovoUsuarioInput com as informações do usuário recém-adicionado.
     * @throws RegraDeNegocioException Caso ocorra um erro na execução da operação, como usuário já cadastrado ou usuário menor de idade.
     */

    public AdicionaNovoUsuarioInput adicionaNovoUsuario(AdicionaNovoUsuarioOutput adicionaNovoUsuarioOutput) throws RegraDeNegocioException {
        if (Boolean.TRUE.equals(verificarUsuarioExiste(adicionaNovoUsuarioOutput.username()))) {
            throw new RegraDeNegocioException(
                    HttpStatus.CONFLICT,
                    "Usuário já cadastrado",
                    "Este username já existe no sistema, tente outro.");
        }

        long idade = ChronoUnit.YEARS.between(adicionaNovoUsuarioOutput.dataNascimento(), LocalDate.now());
        if (idade < 18) {
            throw new RegraDeNegocioException(
                    HttpStatus.FORBIDDEN,
                    "Usuário é menor de idade",
                    "Não é possível adicionar um Usuário menor de idade, pois o sistema vende bebidas alcoólicas.");
        }

        Usuario usuario = usuarioRepository.saveAndFlush(mapOutputToUsuario(adicionaNovoUsuarioOutput));
        return mapUsuarioToInput(usuario);
    }

    /**
     * Busca todos os usuários cadastrados no sistema.
     *
     * @return Collection<BuscaTodosUsuarios> com as informações de todos os usuários cadastrados.
     * @throws RegraDeNegocioException Caso não haja usuários cadastrados no sistema.
     */

    public Collection<BuscaTodosUsuarios> buscarTodosUsuarios() throws RegraDeNegocioException {
        Collection<BuscaTodosUsuarios> buscaTodosUsuarios = usuarioRepository.findAll().stream()
                .map(UsuarioMapper::mapUsuarioToBuscaTodos)
                .toList();

        if (buscaTodosUsuarios.isEmpty()) {
            throw new RegraDeNegocioException(
                    HttpStatus.NOT_FOUND,
                    "Sem usuários cadastrados",
                    "A lista de usuários cadastrados no sistema está vazia, tente cadastrar alguns."
            );
        }

        return buscaTodosUsuarios;
    }

    /**
     * Busca um usuário no sistema pelo nome de usuário.
     *
     * @param username O nome de usuário do usuário a ser buscado.
     * @return Usuario representando o usuário encontrado.
     * @throws RegraDeNegocioException Caso o usuário não seja encontrado no sistema.
     */

    public Usuario buscarUsuarioPorUsername(String username) throws RegraDeNegocioException {
        return usuarioRepository.buscaUsuarioPorUsername(username)
                .orElseThrow(() ->
                        new RegraDeNegocioException(
                                HttpStatus.NOT_FOUND,
                                "Usuário não encontrado",
                                "Não foi encontrado o usuário com este username."
                        ));
    }

    /**
     * Edita as informações de um usuário existente no sistema, identificado pelo seu ID.
     *
     * @param id    O ID do usuário a ser editado.
     * @param model As novas informações a serem atualizadas no usuário.
     * @throws RegraDeNegocioException Caso o usuário não seja encontrado no sistema.
     */

    public void editarUsuarioPorId(Integer id, EditaUsuarioOutput model) throws RegraDeNegocioException {
        Usuario editaUsuario = validarAtributos(model, usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado",
                        "Não foi encontrado o usuário com este username."
                )));
        usuarioRepository.editaUsuarioById(editaUsuario.getNome(),
                editaUsuario.getSobrenome(), id);
    }

    /**
     * Deleta um usuário existente no sistema, identificado pelo seu nome de usuário.
     *
     * @param username O nome de usuário do usuário a ser excluído.
     * @throws RegraDeNegocioException Caso o usuário não seja encontrado no sistema.
     */

    public void deletarUsuarioPorUsername(String username) throws RegraDeNegocioException {
        Usuario usuario = usuarioRepository.buscaUsuarioPorUsername(username)
                .orElseThrow(() -> new RegraDeNegocioException(
                        HttpStatus.NOT_FOUND,
                        "Usuário não encontrado",
                        "Não foi encontrado o usuário com este username."
                ));
        usuarioRepository.delete(usuario);
    }

    /**
     * Verifica se o usuário existente, identificado pelo seu nome de usuário.
     *
     */

    private Boolean verificarUsuarioExiste(String username) {
        return usuarioRepository.buscaUsuarioPorUsername(username).isPresent();
    }

    /**
     * Valida os atributos dos usuários.
     *
     */

    private Usuario validarAtributos(EditaUsuarioOutput model, Usuario usuario) {
        return Usuario.builder()
                .nome(model.nome().isBlank() ? usuario.getNome() : model.nome())
                .sobrenome(model.sobrenome().isBlank() ? usuario.getSobrenome() : model.sobrenome())
                .build();
    }
}
