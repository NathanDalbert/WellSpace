package com.WellSpace.modules.localidades.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LocalidadesRequest(
        @NotBlank(message = "Nome da localidade não pode ser vazio.") String nome_local,
        @NotBlank(message = "Descrição não pode ser vazia.") String descricao,
        @NotNull(message = "Localização não pode ser nula.") String localizacao,
        @NotNull(message = "O locador não pode ser nulo.") UUID locador
        ) {
}
