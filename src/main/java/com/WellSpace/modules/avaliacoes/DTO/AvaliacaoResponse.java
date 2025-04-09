package com.WellSpace.modules.avaliacoes.DTO;

import java.util.UUID;

public record AvaliacaoResponse(UUID id, UUID salaId, Integer quantEstrelas) {
}