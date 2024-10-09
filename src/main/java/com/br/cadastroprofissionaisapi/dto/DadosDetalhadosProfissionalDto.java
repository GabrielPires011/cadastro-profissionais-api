package com.br.cadastroprofissionaisapi.dto;

import com.br.cadastroprofissionaisapi.model.Cargo;

import java.time.LocalDate;

public record DadosDetalhadosProfissionalDto(Long id,
                                             String nome,
                                             Cargo cargo,
                                             LocalDate nascimento,
                                             LocalDate createdDate) {
}
