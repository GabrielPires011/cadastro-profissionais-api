package com.br.cadastroprofissionaisapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CriarContatoDto(
        @NotBlank(message = "O nome não pode estar em branco.")
        @Size(max = 255, message = "O nome não pode ter mais de 100 caracteres.")
        String nome,
        @NotBlank(message = "O contato não pode estar em branco.")
        @Size(max = 255, message = "O contato não pode ter mais de 255 caracteres.")
        String contato,
        @NotNull(message = "O idProfissional não pode ser nulo.")
        Long idProfissional) {
}
