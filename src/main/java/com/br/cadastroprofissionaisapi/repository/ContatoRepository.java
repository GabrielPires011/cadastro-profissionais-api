package com.br.cadastroprofissionaisapi.repository;

import com.br.cadastroprofissionaisapi.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepository extends JpaRepository<Profissional, Long> {

    @Modifying
    @Query("delete from Contato c WHERE c.profissional.id = :idProfissional")
    void deletarContatosPorProfissional(@Param("idProfissional") Long idProfissional);
}
