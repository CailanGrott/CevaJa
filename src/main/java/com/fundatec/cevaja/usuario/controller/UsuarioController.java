package com.fundatec.cevaja.usuario.controller;

import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioOutput;
import com.fundatec.cevaja.usuario.model.dto.EditaUsuarioOutput;
import com.fundatec.cevaja.usuario.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<AdicionaNovoUsuarioInput> adicionaNovoUsuario(@RequestBody AdicionaNovoUsuarioOutput adicionaNovoUsuarioOutput) {
        AdicionaNovoUsuarioInput adicionaNovoUsuarioInput = usuarioService.adicionaNovoUsuario(adicionaNovoUsuarioOutput);
        return new ResponseEntity<>(adicionaNovoUsuarioInput, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<AdicionaNovoUsuarioInput>> buscaTodosUsuarios() {
        Iterable<AdicionaNovoUsuarioInput> adicionaNovoUsuarioInput = usuarioService.buscaTodosUsuarios();
        return new ResponseEntity<>(adicionaNovoUsuarioInput, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> editaUsuario(@PathVariable("id") Integer id,
                                             @RequestBody EditaUsuarioOutput model) {
        usuarioService.editaUsuarioPorId(id, model);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable("id") Integer id) {
        usuarioService.deletaUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

}
