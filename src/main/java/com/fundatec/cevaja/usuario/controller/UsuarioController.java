package com.fundatec.cevaja.usuario.controller;

import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioOutput;
import com.fundatec.cevaja.usuario.model.dto.BuscaTodosUsuarios;
import com.fundatec.cevaja.usuario.model.dto.EditaUsuarioOutput;
import com.fundatec.cevaja.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    /**
     * Endpoint para adicionar um novo usuário no sistema.
     *
     * @param adicionaNovoUsuarioOutput As informações do novo usuário a ser adicionado.
     * @return ResponseEntity com status 201 (CREATED) e o objeto AdicionaNovoUsuarioInput representando o usuário recém-adicionado.
     * @throws RegraDeNegocioException Caso ocorra um erro na execução da operação.
     */

    @PostMapping
    public ResponseEntity<AdicionaNovoUsuarioInput> adicionaNovoUsuario(@RequestBody AdicionaNovoUsuarioOutput adicionaNovoUsuarioOutput) throws RegraDeNegocioException {
        AdicionaNovoUsuarioInput adicionaNovoUsuarioInput = usuarioService.adicionaNovoUsuario(adicionaNovoUsuarioOutput);
        return new ResponseEntity<>(adicionaNovoUsuarioInput, HttpStatus.CREATED);
    }

    /**
     * Endpoint para buscar todos os usuários cadastrados no sistema.
     *
     * @return ResponseEntity com status 200 (OK) e uma coleção de objetos BuscaTodosUsuarios representando os usuários cadastrados.
     * @throws RegraDeNegocioException Caso ocorra um erro na execução da operação.
     */

    @GetMapping
    public ResponseEntity<Collection<BuscaTodosUsuarios>> buscaTodosUsuarios() throws RegraDeNegocioException {
        Collection<BuscaTodosUsuarios> adicionaNovoUsuarioInput = usuarioService.buscarTodosUsuarios();
        return new ResponseEntity<>(adicionaNovoUsuarioInput, HttpStatus.OK);
    }

    /**
     * Endpoint para editar as informações de um usuário existente no sistema, identificado pelo seu ID.
     *
     * @param id    O ID do usuário a ser editado.
     * @param model As novas informações a serem atualizadas no usuário.
     * @return ResponseEntity com status 204 (NO CONTENT) em caso de sucesso.
     * @throws RegraDeNegocioException Caso ocorra um erro na execução da operação.
     */

    @PutMapping("{id}")
    public ResponseEntity<Void> editaUsuario(@PathVariable("id") Integer id,
                                             @RequestBody EditaUsuarioOutput model) throws RegraDeNegocioException {
        usuarioService.editarUsuarioPorId(id, model);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Endpoint para deletar um usuário existente no sistema, identificado pelo seu nome de usuário.
     *
     * @param username O nome de usuário a ser excluído.
     * @return ResponseEntity com status 202 (ACCEPTED) em caso de sucesso.
     * @throws RegraDeNegocioException Caso ocorra um erro na execução da operação.
     */

    @DeleteMapping("{username}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("username") String username) throws RegraDeNegocioException {
        usuarioService.deletarUsuarioPorUsername(username);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
