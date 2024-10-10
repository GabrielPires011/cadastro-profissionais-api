package com.br.cadastroprofissionaisapi.dto;

import java.time.LocalDate;

public record DadosDetalhadosContatoDto(
         Long id,
         String nome,
         String contato,
         LocalDate createdDate,
         Long idProfissional
) {
}