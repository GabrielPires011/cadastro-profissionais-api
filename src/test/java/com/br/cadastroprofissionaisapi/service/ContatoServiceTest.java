package com.br.cadastroprofissionaisapi.service;

import com.br.cadastroprofissionaisapi.dto.*;
import com.br.cadastroprofissionaisapi.exception.ValidacaoException;
import com.br.cadastroprofissionaisapi.model.Cargo;
import com.br.cadastroprofissionaisapi.model.Contato;
import com.br.cadastroprofissionaisapi.model.Profissional;
import com.br.cadastroprofissionaisapi.repository.ContatoRepository;
import com.br.cadastroprofissionaisapi.repository.ProfissionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContatoServiceTest {

    @InjectMocks
    private ContatoService contatoService;
    @Mock
    private ProfissionalRepository profissionalRepository;
    @Mock
    private ContatoRepository contatoRepository;

    @Captor
    private ArgumentCaptor<Contato> cantatoCaptor;

    @Test
    void deveriaCriarContato() {
        var dto = new CriarContatoDto("Gabriel", "12123451234", 1L);
        var profissional = new Profissional(1L, dto.nome(), Cargo.DESENVOLVEDOR,
                LocalDate.now(), LocalDate.now());
        var contato = new Contato(1L, dto.nome(), "12123451234",
                LocalDate.now(), profissional);

        when(profissionalRepository.existsById(any())).thenReturn(true);
        when(contatoRepository.save(any())).thenReturn(contato);

        var id = contatoService.criar(dto);

        assertNotNull(id, "O ID do contato criado não deve ser nulo");
    }

    @Test
    void naoDeveriaCriarPorCausaQueNaoExisteProfissional() {
        var dto = new CriarContatoDto("Gabriel", "12123451234", 1L);

        when(profissionalRepository.existsById(any())).thenReturn(false);

        assertThrows(ValidacaoException.class, () -> contatoService.criar(dto));
    }

    @Test
    void deveriaAlterarContato() {
        var alterarProfissionalDto = new AlterarContatoDto("Gabriel Teixeira", "22123451234", 1L);
        var profissional = new Profissional(1L, "Gabriel", Cargo.DESENVOLVEDOR,
                LocalDate.now(), LocalDate.now());
        var contato = new Contato(1L, "Gabriel", "12123451234",
                LocalDate.now(), profissional);

        when(profissionalRepository.existsById(any())).thenReturn(true);
        given(contatoRepository.findById(any())).willReturn(Optional.of(contato));
        when(contatoRepository.save(any())).thenReturn(contato);

        contatoService.alterar(1L, alterarProfissionalDto);

        then(contatoRepository).should().save(cantatoCaptor.capture());
        var cantatoCaptorValue = cantatoCaptor.getValue();

        assertEquals(contato.getId(), cantatoCaptorValue.getId());
        assertEquals(contato.getNome(), cantatoCaptorValue.getNome());
        assertEquals(contato.getCreatedDate(), cantatoCaptorValue.getCreatedDate());
    }

    @Test
    void naoDeveriaAlterarPorCausaQueNaoExisteProfissional() {
        var alterarContatoDto = new AlterarContatoDto("Gabriel Teixeira", "12123451234", 1L);

        when(profissionalRepository.existsById(any())).thenReturn(false);

        assertThrows(ValidacaoException.class, () -> contatoService.alterar(1L, alterarContatoDto));
    }

    @Test
    void naoDeveriaAlterarPorCausaQueNaoExisteContato() {
        var alterarContatoDto = new AlterarContatoDto("Gabriel Teixeira", "12123451234", 1L);

        when(profissionalRepository.existsById(any())).thenReturn(true);
        given(contatoRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(ValidacaoException.class, () -> contatoService.alterar(1L, alterarContatoDto));
    }

    @Test
    void deveriaExcluirProfissional() {
        var profissional = new Profissional(1L, "Gabriel", Cargo.DESENVOLVEDOR, LocalDate.now(), LocalDate.now());
        var contato = new Contato(1L, "Gabriel", "12123451234",
                LocalDate.now(), profissional);

        given(contatoRepository.findById(1L)).willReturn(Optional.of(contato));

        contatoService.excluir(1L);

        then(contatoRepository).should().delete(contato);
    }

    @Test
    void naoDeveriaExcluirPorCausaQueNaoExiste() {
        given(contatoRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(ValidacaoException.class, () -> contatoService.excluir(1L));
    }
}