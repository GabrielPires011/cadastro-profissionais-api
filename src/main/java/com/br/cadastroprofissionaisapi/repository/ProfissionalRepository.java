package com.br.cadastroprofissionaisapi.repository;

import com.br.cadastroprofissionaisapi.dto.DadosDetalhadosProfissionalDto;
import com.br.cadastroprofissionaisapi.model.Cargo;
import com.br.cadastroprofissionaisapi.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    @Query("select new com.br.cadastroprofissionaisapi.dto.DadosDetalhadosProfissionalDto(p.id, p.nome, p.cargo, p.nascimento, p.createdDate)" +
            " from Profissional p where p.id = :id")
    DadosDetalhadosProfissionalDto buscarDadosDetalhadosProfissionalDto(@Param("id") Long id);

    @Query("select count (p) > 0 from Profissional p where p.nome = :nome and p.cargo = :cargo and p.nascimento = :nascimento")
    boolean buscarProfissionalRepetido(@Param("nome") String nome,
                                       @Param("cargo") Cargo cargo,
                                       @Param("nascimento") LocalDate nascimento);

    @Query("select new com.br.cadastroprofissionaisapi.dto.DadosDetalhadosProfissionalDto(p.id, p.nome, p.cargo, p.nascimento, p.createdDate) " +
            "from Profissional p " +
            "where :query is null " +
            "or lower(p.nome) like lower(concat('%', :query, '%')) " +
            "or lower(str(p.cargo)) like lower(concat('%', :query, '%'))")
    List<DadosDetalhadosProfissionalDto> buscarPorQuery(@Param("query") String query);


}
