package com.WellSpace.modules.avaliacoes.service.interfaces;

import java.util.UUID;

import com.WellSpace.modules.avaliacoes.DTO.AvaliacaoRequest;
import com.WellSpace.modules.avaliacoes.DTO.AvaliacaoResponse;

public interface AvaliacaoServiceInterface {

    AvaliacaoResponse avaliarSala(AvaliacaoRequest request);

    Double obterMediaEstrelasPorSala(UUID salaId);
}
