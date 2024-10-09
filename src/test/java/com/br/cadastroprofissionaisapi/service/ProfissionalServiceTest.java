package com.br.cadastroprofissionaisapi.service;

import com.br.cadastroprofissionaisapi.dto.AlterarProfissionalDto;
import com.br.cadastroprofissionaisapi.dto.CriarProfissionalDto;
import com.br.cadastroprofissionaisapi.dto.DadosDetalhadosProfissionalDto;
import com.br.cadastroprofissionaisapi.exception.ValidacaoException;
import com.br.cadastroprofissionaisapi.model.Cargo;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfissionalServiceTest {

    @InjectMocks
    private ProfissionalService profissionalService;
    @Mock
    private ProfissionalRepository profissionalRepository;
    @Mock
    private ContatoRepository contatoRepository;

    @Captor
    private ArgumentCaptor<Profissional> profissionalCaptor;

    @Test
    void deveriaCriarProfissional() {
        var dto = new CriarProfissionalDto("Gabriel", Cargo.TESTER, LocalDate.now());
        var profissional = new Profissional(1L, dto.nome(), dto.cargo(),
                dto.nascimento(), LocalDate.now());

        when(profissionalRepository.save(any())).thenReturn(profissional);

        var id = profissionalService.criar(dto);

        assertNotNull(id, "O ID do profissional criado não deve ser nulo");
    }

    @Test
    void naoDeveriaCriarPorCausaDeJaCadastrado() {
        var dto = new CriarProfissionalDto("Gabriel", Cargo.TESTER, LocalDate.now());

        given(profissionalRepository.buscarProfissionalRepetido(any(), any(), any())).willReturn(true);

        assertThrows(ValidacaoException.class, () -> profissionalService.criar(dto));
    }

    @Test
    void deveriaBuscarProfissionalPeloId() {
        var dadosDetalhadosProfissionalDto = new DadosDetalhadosProfissionalDto(1L, "Gabriel", Cargo.DESENVOLVEDOR,
                LocalDate.now(), LocalDate.now());

        given(profissionalRepository.buscarDadosDetalhadosProfissionalDto(anyLong())).willReturn(dadosDetalhadosProfissionalDto);

        var dto = profissionalService.buscarPorId(1L);

        assertNotNull(dto, "O profissional buscado não deve ser nulo");
        assertEquals(1L, dto.id(), "O ID do profissional buscado deve ser o esperado");
        assertEquals("Gabriel", dto.nome(), "O nome do profissional deve ser 'Gabriel'");
    }

    @Test
    void naoDeveriaBuscarProfissionalPeloIdPorCausaQueNaoExiste() {
        given(profissionalRepository.buscarDadosDetalhadosProfissionalDto(anyLong())).willReturn(null);

        assertThrows(ValidacaoException.class, () -> profissionalService.buscarPorId(1L));
    }

    @Test
    void deveriaAlterarProfissional() {
        var alterarProfissionalDto = new AlterarProfissionalDto("Gabriel Teixeira", Cargo.DESENVOLVEDOR,
                LocalDate.of(2010, 10, 11));
        var profissional = new Profissional(1L, "Gabriel", Cargo.TESTER,
                LocalDate.of(2010, 10, 10), LocalDate.of(2024, 10, 10));

        given(profissionalRepository.findById(any())).willReturn(Optional.of(profissional));

        when(profissionalRepository.save(any())).thenReturn(profissional);

        profissionalService.alterar(1L, alterarProfissionalDto);

        then(profissionalRepository).should().save(profissionalCaptor.capture());
        var profissionalSalvo = profissionalCaptor.getValue();

        assertEquals(profissional.getId(), profissionalSalvo.getId());
        assertEquals(profissional.getNome(), profissionalSalvo.getNome());
        assertEquals(profissional.getCargo(), profissionalSalvo.getCargo());
        assertEquals(profissional.getNascimento(), profissionalSalvo.getNascimento());
    }

    @Test
    void naoDeveriaAlterarPorCausaQueNaoExiste() {
        var alterarProfissionalDto = new AlterarProfissionalDto("Gabriel Teixeira", Cargo.DESENVOLVEDOR, LocalDate.now());

        given(profissionalRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(ValidacaoException.class, () -> profissionalService.alterar(1L, alterarProfissionalDto));
    }

    @Test
    void deveriaExcluirProfissional() {
        var profissional = new Profissional(1L, "Gabriel", Cargo.DESENVOLVEDOR, LocalDate.now(), LocalDate.now());

        given(profissionalRepository.findById(1L)).willReturn(Optional.of(profissional));

        profissionalService.excluir(1L);

        then(contatoRepository).should().deletarContatosPorProfissional(1L);

        then(profissionalRepository).should().delete(profissional);
    }

    @Test
    void naoDeveriaExcluirPorCausaQueNaoExiste() {
        given(profissionalRepository.findById(any())).willReturn(Optional.empty());

        assertThrows(ValidacaoException.class, () -> profissionalService.excluir(1L));
    }

    @Test
    void deveriaBuscarProfissionaisComTodosCampos() {
        var profissionais = List.of(
                new DadosDetalhadosProfissionalDto(1L, "Gabriel", Cargo.DESENVOLVEDOR, LocalDate.now(), LocalDate.now())
        );

        given(profissionalRepository.buscarPorQuery(null)).willReturn(profissionais);

        var resultado = profissionalService.buscarProfissionais(null, null);

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).containsKey("id"));
        assertTrue(resultado.get(0).containsKey("nome"));
        assertTrue(resultado.get(0).containsKey("cargo"));
        assertTrue(resultado.get(0).containsKey("nascimento"));
        assertTrue(resultado.get(0).containsKey("createdDate"));
    }

    @Test
    void deveriaBuscarProfissionaisComCamposFiltrados() {
        var profissionais = List.of(
                new DadosDetalhadosProfissionalDto(1L, "Gabriel", Cargo.DESENVOLVEDOR, LocalDate.now(), LocalDate.now())
        );

        given(profissionalRepository.buscarPorQuery("Gabriel")).willReturn(profissionais);

        var campos = List.of("id", "nome");

        var resultado = profissionalService.buscarProfissionais("Gabriel", campos);

        assertEquals(1, resultado.size());
        var profissional = resultado.get(0);
        assertTrue(profissional.containsKey("id"));
        assertTrue(profissional.containsKey("nome"));
        assertFalse(profissional.containsKey("cargo"));
        assertFalse(profissional.containsKey("nascimento"));
    }

}