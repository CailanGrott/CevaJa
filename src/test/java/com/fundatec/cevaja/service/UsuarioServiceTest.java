package com.fundatec.cevaja.service;


import com.fundatec.cevaja.exception.RegraDeNegocioException;
import com.fundatec.cevaja.usuario.model.Usuario;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioInput;
import com.fundatec.cevaja.usuario.model.dto.AdicionaNovoUsuarioOutput;
import com.fundatec.cevaja.usuario.model.dto.EditaUsuarioOutput;
import com.fundatec.cevaja.usuario.repository.UsuarioRepository;
import com.fundatec.cevaja.usuario.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdicionaNovoUsuario() throws RegraDeNegocioException {
        AdicionaNovoUsuarioOutput output = new AdicionaNovoUsuarioOutput(
                "Cailan",
                "Soares Grott",
                LocalDate.of(2002, 8, 28),
                "04471087002",
                "cailan",
                "Caica122014"
        );
        when(usuarioRepository.buscaUsuarioPorUsername(any())).thenReturn(Optional.empty());
        when(usuarioRepository.saveAndFlush(any())).thenReturn(new Usuario());
        AdicionaNovoUsuarioInput result = usuarioService.adicionaNovoUsuario(output);
        verify(usuarioRepository, times(1)).buscaUsuarioPorUsername(any());
        verify(usuarioRepository, times(1)).saveAndFlush(any());
        assertNotNull(result);
    }

    @Test
    void testBuscaTodosUsuarios() throws RegraDeNegocioException {
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(RegraDeNegocioException.class, () -> usuarioService.buscarTodosUsuarios());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testBuscaUsuarioPorUsername() throws RegraDeNegocioException {
        String username = "username";
        when(usuarioRepository.buscaUsuarioPorUsername(username)).thenReturn(Optional.of(new Usuario()));
        Usuario result = usuarioService.buscarUsuarioPorUsername(username);
        verify(usuarioRepository, times(1)).buscaUsuarioPorUsername(username);
        assertNotNull(result);
    }

    @Test
    void testBuscaUsuarioPorUsernameUsuarioNaoEncontrado() {
        String username = "username";
        when(usuarioRepository.buscaUsuarioPorUsername(username)).thenReturn(Optional.empty());
        assertThrows(RegraDeNegocioException.class, () -> usuarioService.buscarUsuarioPorUsername(username));
        verify(usuarioRepository, times(1)).buscaUsuarioPorUsername(username);
    }

    @Test
    void testEditaUsuarioPorId() throws RegraDeNegocioException {
        Integer userId = 1;
        EditaUsuarioOutput output = new EditaUsuarioOutput("Nome Editado", "Sobrenome Editado");
        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(new Usuario()));
        doNothing().when(usuarioRepository).editaUsuarioById(any(), any(), any());
        assertDoesNotThrow(() -> usuarioService.editarUsuarioPorId(userId, output));
        verify(usuarioRepository, times(1)).findById(userId);
        verify(usuarioRepository, times(1)).editaUsuarioById(any(), any(), any());
    }

    @Test
    void testDeletaUsuarioPorUsername() throws RegraDeNegocioException {
        String username = "username";
        when(usuarioRepository.buscaUsuarioPorUsername(username)).thenReturn(Optional.of(new Usuario()));
        doNothing().when(usuarioRepository).delete(any());
        usuarioService.deletarUsuarioPorUsername(username);
        verify(usuarioRepository, times(1)).buscaUsuarioPorUsername(username);
        verify(usuarioRepository, times(1)).delete(any());
    }

    @Test
    void testDeletaUsuarioPorUsernameUsuarioNaoEncontrado() {
        String username = "username";
        when(usuarioRepository.buscaUsuarioPorUsername(username)).thenReturn(Optional.empty());
        assertThrows(RegraDeNegocioException.class, () -> usuarioService.deletarUsuarioPorUsername(username));
        verify(usuarioRepository, times(1)).buscaUsuarioPorUsername(username);
    }
}

