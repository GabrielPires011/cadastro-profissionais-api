package com.br.cadastroprofissionaisapi.model;

import com.br.cadastroprofissionaisapi.dto.AlterarContatoDto;
import com.br.cadastroprofissionaisapi.dto.CriarContatoDto;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "contatos")
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String contato;
    private LocalDate createdDate;
    @ManyToOne
    private Profissional profissional;

    public Contato(CriarContatoDto dto) {
        this.nome = dto.nome();
        this.contato = dto.contato();
        this.profissional = new Profissional(dto.idProfissional());
        this.createdDate = LocalDate.now();
    }
    public Contato() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void alterar(AlterarContatoDto dto) {
        this.nome = dto.nome();
        this.contato = dto.contato();
        this.profissional = new Profissional(dto.idProfissional());
    }

    public Contato(Long id, String nome, String contato, LocalDate createdDate, Profissional profissional) {
        this.id = id;
        this.nome = nome;
        this.contato = contato;
        this.createdDate = createdDate;
        this.profissional = profissional;
    }
}
