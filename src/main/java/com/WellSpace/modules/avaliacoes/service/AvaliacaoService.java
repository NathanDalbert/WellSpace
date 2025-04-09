package com.WellSpace.modules.avaliacoes.service;

import com.WellSpace.modules.avaliacoes.DTO.AvaliacaoRequest;
import com.WellSpace.modules.avaliacoes.DTO.AvaliacaoResponse;
import com.WellSpace.modules.avaliacoes.domain.Avaliacao;
import com.WellSpace.modules.avaliacoes.repository.AvaliacaoRepository;
import com.WellSpace.modules.avaliacoes.service.Mapper.AvaliacaoMapper;
import com.WellSpace.modules.avaliacoes.service.interfaces.AvaliacaoServiceInterface;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.salas.repository.SalasRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AvaliacaoService implements AvaliacaoServiceInterface {

    private final AvaliacaoRepository repository;
    private final SalasRepository salasRepository;
    private final AvaliacaoMapper mapper;

    @Override
    public AvaliacaoResponse avaliarSala(AvaliacaoRequest request) {
        Salas sala = salasRepository.findById(request.salaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        Avaliacao avaliacao = mapper.toEntity(request.quantEstrelas(), sala);
        return mapper.toResponse(repository.save(avaliacao));
    }

    @Override
    public Double obterMediaEstrelasPorSala(UUID salaId) {
        return repository.mediaEstrelasPorSala(salaId);
    }
}
