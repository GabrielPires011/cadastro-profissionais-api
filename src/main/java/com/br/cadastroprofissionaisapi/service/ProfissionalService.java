package com.br.cadastroprofissionaisapi.service;

import com.br.cadastroprofissionaisapi.dto.AlterarProfissionalDto;
import com.br.cadastroprofissionaisapi.dto.CriarProfissionalDto;
import com.br.cadastroprofissionaisapi.dto.DadosDetalhadosProfissionalDto;
import com.br.cadastroprofissionaisapi.exception.ValidacaoException;
import com.br.cadastroprofissionaisapi.model.Profissional;
import com.br.cadastroprofissionaisapi.repository.ContatoRepository;
import com.br.cadastroprofissionaisapi.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.br.cadastroprofissionaisapi.util.CampoExtractorUtil.extrairCampos;
import static com.br.cadastroprofissionaisapi.util.CampoExtractorUtil.filtrarCampos;
import static com.br.cadastroprofissionaisapi.util.MensagensUtil.PROFISSIONAL_JA_CADASTRADO;
import static com.br.cadastroprofissionaisapi.util.MensagensUtil.PROFISSIONAL_NAO_ENCONTRADO;

@Service
public class ProfissionalService {

    private final ProfissionalRepository repository;
    private final ContatoRepository contatoRepository;

    public ProfissionalService(ProfissionalRepository repository, ContatoRepository contatoRepository) {
        this.repository = repository;
        this.contatoRepository = contatoRepository;
    }

    public DadosDetalhadosProfissionalDto buscarPorId(Long id) {
        var dto = repository.buscarDadosDetalhadosProfissionalDto(id);
        if (dto == null) throw new ValidacaoException(PROFISSIONAL_NAO_ENCONTRADO);
        return dto;
    }

    @Transactional
    public Long criar(CriarProfissionalDto dto) {
        if (repository.buscarProfissionalRepetido(dto.nome(), dto.cargo(), dto.nascimento()))
            throw new ValidacaoException(PROFISSIONAL_JA_CADASTRADO);

        var profissional = new Profissional(dto);
        repository.save(profissional);
        return profissional.getId();
    }

    @Transactional
    public void alterar(Long id, AlterarProfissionalDto dto) {
        var profissional = repository.findById(id)
                .orElseThrow(() -> new ValidacaoException(
                        PROFISSIONAL_NAO_ENCONTRADO));

        profissional.alterar(dto);

        repository.save(profissional);
    }

    @Transactional
    public void excluir(Long id) {
        var profissional = repository.findById(id)
                .orElseThrow(() -> new ValidacaoException(
                        PROFISSIONAL_NAO_ENCONTRADO));

        contatoRepository.deletarContatosPorProfissional(id);

        repository.delete(profissional);
    }

    public List<Map<String, Object>> buscarProfissionais(String consulta, List<String> campos) {
        var profissionais = (consulta == null || consulta.isEmpty())
                ? repository.buscarPorQuery(null)
                : repository.buscarPorQuery(consulta);

        return campos != null && !campos.isEmpty()
                ? filtrarCampos(profissionais, campos)
                : profissionais.stream().map(this::dtoParaMapa).toList();
    }

    private Map<String, Object> dtoParaMapa(DadosDetalhadosProfissionalDto profissional) {
        return extrairCampos(profissional, List.of("id", "nome", "cargo", "nascimento", "createdDate"));
    }
}