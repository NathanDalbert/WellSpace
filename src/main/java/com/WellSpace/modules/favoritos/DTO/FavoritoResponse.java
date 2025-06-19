package com.WellSpace.modules.favoritos.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record FavoritoResponse(

        @NotNull(message = "O ID do favorito não pode ser nulo")
        @Schema(example = "555e1234-e21b-45d3-a456-426614174000", description = "ID único do favorito")
        UUID favoritoId,

        @NotNull(message = "O ID do usuário não pode ser nulo")
        @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "ID do usuário que favoritou a sala")
        UUID usuarioId,

        @NotNull(message = "O ID da sala não pode ser nulo")
        @Schema(example = "987e6543-e21b-45d3-a456-426614174999", description = "ID da sala favoritada")
        UUID salaId
) {
}
