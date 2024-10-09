package com.br.cadastroprofissionaisapi.model;

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
}
