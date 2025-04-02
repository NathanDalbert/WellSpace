package com.WellSpace.modules.contato.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ContatoRequest(
        @NotNull(message = "O ID do usuário é obrigatório") UUID usuario_id,

        @NotBlank(message = "O contato não pode ser vazio") String contato) {
}
