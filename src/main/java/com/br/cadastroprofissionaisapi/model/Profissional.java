package com.br.cadastroprofissionaisapi.model;

import com.br.cadastroprofissionaisapi.dto.AlterarProfissionalDto;
import com.br.cadastroprofissionaisapi.dto.CriarProfissionalDto;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "profissionais")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Cargo cargo;
    private LocalDate nascimento;
    private LocalDate createdDate;

    public Profissional(CriarProfissionalDto dto) {
        this.nome = dto.nome();
        this.cargo = dto.cargo();
        this.nascimento = dto.nascimento();
    }

    public Profissional() {}


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void alterar(AlterarProfissionalDto dto) {
        this.nome = dto.nome();
        this.cargo = dto.cargo();
        this.nascimento = dto.nascimento();
    }

    public Profissional(Long id, String nome, Cargo cargo, LocalDate nascimento, LocalDate createdDate) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.nascimento = nascimento;
        this.createdDate = createdDate;
    }
}
