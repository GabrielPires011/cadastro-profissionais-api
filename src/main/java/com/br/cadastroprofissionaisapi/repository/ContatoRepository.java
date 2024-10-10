package com.br.cadastroprofissionaisapi.repository;

import com.br.cadastroprofissionaisapi.dto.DadosDetalhadosContatoDto;
import com.br.cadastroprofissionaisapi.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    @Modifying
    @Query("delete from Contato c WHERE c.profissional.id = :idProfissional")
    void deletarContatosPorProfissional(@Param("idProfissional") Long idProfissional);

    @Query("select new com.br.cadastroprofissionaisapi.dto.DadosDetalhadosContatoDto(" +
            "c.id, c.nome, c.contato, c.createdDate, c.profissional.id)" +
            " from Contato c where c.id = :id")
    DadosDetalhadosContatoDto buscarDadosDetalhadosContatoDto(Long id);

    @Query("select new com.br.cadastroprofissionaisapi.dto.DadosDetalhadosContatoDto(" +
            "c.id, c.nome, c.contato, c.createdDate, p.id)" +
            "from Contato c " +
            "inner join Profissional p on p.id = c.id " +
            "where :query is null " +
            "or lower(c.nome) like lower(concat('%', :query, '%')) " +
            "or lower(c.contato) like lower(concat('%', :query, '%'))")
    List<DadosDetalhadosContatoDto> buscarPorQuery(@Param("query") String query);
}
