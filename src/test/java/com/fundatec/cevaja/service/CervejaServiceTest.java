package com.fundatec.cevaja.service;

import com.fundatec.cevaja.cerveja.model.Cerveja;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaInput;
import com.fundatec.cevaja.cerveja.model.dto.AdicionaNovoTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.model.dto.EditaTipoCervejaOutput;
import com.fundatec.cevaja.cerveja.repository.CervejaRepository;
import com.fundatec.cevaja.cerveja.service.CervejaService;
import com.fundatec.cevaja.exception.RegraDeNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CervejaServiceTest {

    @Mock
    private CervejaRepository cervejaRepository;

    @InjectMocks
    private CervejaService cervejaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdicionaNovoTipoCerveja() throws RegraDeNegocioException {
        AdicionaNovoTipoCervejaOutput output = new AdicionaNovoTipoCervejaOutput(
                "IPA",
                BigDecimal.valueOf(15.0)
        );
        when(cervejaRepository.buscaTipoCerveja(any())).thenReturn(Optional.empty());
        when(cervejaRepository.saveAndFlush(any())).thenReturn(new Cerveja());
        AdicionaNovoTipoCervejaInput result = cervejaService.adicionaNovoTipoCerveja(output);
        verify(cervejaRepository, times(1)).buscaTipoCerveja(any());
        verify(cervejaRepository, times(1)).saveAndFlush(any());
        assertNotNull(result);
    }

    @Test
    void testBuscaTodosTiposCerveja() throws RegraDeNegocioException {
        when(cervejaRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(RegraDeNegocioException.class, () -> cervejaService.buscaTodosTiposCerveja());
        verify(cervejaRepository, times(1)).findAll();
    }

    @Test
    void testBuscaTipoCerveja() throws RegraDeNegocioException {
        when(cervejaRepository.findAll()).thenReturn(Collections.singletonList(new Cerveja()));
        Collection<AdicionaNovoTipoCervejaInput> result = cervejaService.buscaTipoCerveja(Collections.singleton("IPA"));
        verify(cervejaRepository, times(1)).findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testBuscaTipoCervejaNenhumCadastrado() {
        when(cervejaRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(RegraDeNegocioException.class, () -> cervejaService.buscaTipoCerveja(Collections.singleton("IPA")));
        verify(cervejaRepository, times(1)).findAll();
    }

    @Test
    void testEditaTipoCervejaPorId() throws RegraDeNegocioException {
        Integer cervejaId = 1;
        EditaTipoCervejaOutput output = new EditaTipoCervejaOutput("IPA", BigDecimal.valueOf(20.0));
        when(cervejaRepository.findById(cervejaId)).thenReturn(Optional.of(new Cerveja()));
        doNothing().when(cervejaRepository).editaCervejaPorId(any(), any(), any());
        assertDoesNotThrow(() -> cervejaService.editaTipoCervejaPorId(cervejaId, output));
        verify(cervejaRepository, times(1)).findById(cervejaId);
        verify(cervejaRepository, times(1)).editaCervejaPorId(any(), any(), any());
    }

    @Test
    void testDeletaTipoCervejaPorNome() throws RegraDeNegocioException {
        String tipoCerveja = "IPA";
        when(cervejaRepository.buscaTipoCerveja(tipoCerveja)).thenReturn(Optional.of(new Cerveja()));
        doNothing().when(cervejaRepository).delete(any());
        assertDoesNotThrow(() -> cervejaService.deletaTipoCervejaPorNome(tipoCerveja));
        verify(cervejaRepository, times(1)).buscaTipoCerveja(tipoCerveja);
        verify(cervejaRepository, times(1)).delete(any());
    }

    @Test
    void testDeletaTipoCervejaPorNomeNaoEncontrado() {
        String tipoCerveja = "IPA";
        when(cervejaRepository.buscaTipoCerveja(tipoCerveja)).thenReturn(Optional.empty());
        assertThrows(RegraDeNegocioException.class, () -> cervejaService.deletaTipoCervejaPorNome(tipoCerveja));
        verify(cervejaRepository, times(1)).buscaTipoCerveja(tipoCerveja);
    }
}
