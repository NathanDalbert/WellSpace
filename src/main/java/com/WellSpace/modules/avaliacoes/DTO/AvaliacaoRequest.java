package com.WellSpace.modules.avaliacoes.DTO;

import java.util.UUID;

public record AvaliacaoRequest(UUID salaId, Integer quantEstrelas) {
}