package com.fundatec.cevaja.usuario.controller;

import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioOutput;
import com.fundatec.cevaja.usuario.model.dto.BuscaTodosUsuarios;
import com.fundatec.cevaja.usuario.model.dto.EditaUsuarioOutput;
import com.fundatec.cevaja.usuario.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<AdicionaNovoUsuarioInput> adicionaNovoUsuario(@RequestBody AdicionaNovoUsuarioOutput adicionaNovoUsuarioOutput) throws RegraDeNegocioException {
        AdicionaNovoUsuarioInput adicionaNovoUsuarioInput = usuarioService.adicionaNovoUsuario(adicionaNovoUsuarioOutput);
        return new ResponseEntity<>(adicionaNovoUsuarioInput, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<BuscaTodosUsuarios>> buscaTodosUsuarios() throws RegraDeNegocioException {
        Collection<BuscaTodosUsuarios> adicionaNovoUsuarioInput = usuarioService.buscaTodosUsuarios();
        return new ResponseEntity<>(adicionaNovoUsuarioInput, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> editaUsuario(@PathVariable("id") Integer id,
                                             @RequestBody EditaUsuarioOutput model) throws RegraDeNegocioException {
        usuarioService.editaUsuarioPorId(id, model);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{username}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("username") String username) throws RegraDeNegocioException {
        usuarioService.deletaUsuarioPorUsername(username);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
