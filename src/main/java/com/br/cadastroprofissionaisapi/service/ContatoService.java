package com.br.cadastroprofissionaisapi.service;

import com.br.cadastroprofissionaisapi.dto.AlterarContatoDto;
import com.br.cadastroprofissionaisapi.dto.CriarContatoDto;
import com.br.cadastroprofissionaisapi.dto.DadosDetalhadosContatoDto;
import com.br.cadastroprofissionaisapi.exception.ValidacaoException;
import com.br.cadastroprofissionaisapi.model.Contato;
import com.br.cadastroprofissionaisapi.repository.ContatoRepository;
import com.br.cadastroprofissionaisapi.repository.ProfissionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.br.cadastroprofissionaisapi.util.CampoExtractorUtil.extrairCampos;
import static com.br.cadastroprofissionaisapi.util.CampoExtractorUtil.filtrarCampos;
import static com.br.cadastroprofissionaisapi.util.MensagensUtil.CONTATO_NAO_ENCONTRADO;
import static com.br.cadastroprofissionaisapi.util.MensagensUtil.PROFISSIONAL_NAO_ENCONTRADO;

@Service
public class ContatoService {

    private final ContatoRepository repository;

    private final ProfissionalRepository profissionalRepository;

    public ContatoService(ContatoRepository repository, ProfissionalRepository profissionalRepository) {
        this.repository = repository;
        this.profissionalRepository = profissionalRepository;
    }

    @Transactional
    public Long criar(CriarContatoDto dto) {
        if (!profissionalRepository.existsById(dto.idProfissional()))
            throw new ValidacaoException(PROFISSIONAL_NAO_ENCONTRADO);

        var contato = repository.save(new Contato(dto));
        return contato.getId();
    }

    @Transactional
    public void alterar(Long id, AlterarContatoDto dto) {
        if (!profissionalRepository.existsById(dto.idProfissional()))
            throw new ValidacaoException(PROFISSIONAL_NAO_ENCONTRADO);

        var contato = repository.findById(id)
                .orElseThrow(() -> new ValidacaoException(
                        CONTATO_NAO_ENCONTRADO));

        contato.alterar(dto);

        repository.save(contato);
    }

    @Transactional
    public void excluir(Long id) {
        var contato = repository.findById(id)
                .orElseThrow(() -> new ValidacaoException(
                        CONTATO_NAO_ENCONTRADO));

        repository.delete(contato);
    }

    public DadosDetalhadosContatoDto buscarPorId(Long id) {
        var dto = repository.buscarDadosDetalhadosContatoDto(id);
        if (dto == null) throw new ValidacaoException(CONTATO_NAO_ENCONTRADO);
        return dto;
    }

    public List<Map<String, Object>> buscarContatos(String consulta, List<String> campos) {
        var contatos = (consulta == null || consulta.isEmpty())
                ? repository.buscarPorQuery(null)
                : repository.buscarPorQuery(consulta);

        return campos != null && !campos.isEmpty()
                ? filtrarCampos(contatos, campos)
                : contatos.stream().map(this::dtoParaMapa).toList();
    }

    private Map<String, Object> dtoParaMapa(DadosDetalhadosContatoDto dto) {
        return extrairCampos(dto, List.of("id", "nome", "contato", "createdDate", "idProfissional"));
    }
}
