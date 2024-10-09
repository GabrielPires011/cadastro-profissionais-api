package com.br.cadastroprofissionaisapi.dto;

import com.br.cadastroprofissionaisapi.model.Cargo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CriarProfissionalDto(
        @NotBlank(message = "O nome não pode estar em branco.")
        @Size(max = 100, message = "O nome não pode ter mais de 100 caracteres.")
        String nome,
        @NotNull(message = "O cargo não pode ser nulo.")
        Cargo cargo,
        @NotNull(message = "O nascimento não pode ser nulo.")
        LocalDate nascimento
) {
}
