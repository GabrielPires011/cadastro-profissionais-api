package com.br.cadastroprofissionaisapi.dto;

import com.br.cadastroprofissionaisapi.model.Profissional;


import java.time.LocalDate;

public record DadosDetalhadosContatoDto(
         Long id,
         String nome,
         String contato,
         LocalDate createdDate,
         Profissional profissional
) {
}