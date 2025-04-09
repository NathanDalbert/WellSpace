package com.WellSpace.modules.avaliacoes.service.Mapper;

import com.WellSpace.modules.avaliacoes.DTO.AvaliacaoResponse;
import com.WellSpace.modules.avaliacoes.domain.Avaliacao;
import com.WellSpace.modules.salas.domain.Salas;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoMapper {

    public Avaliacao toEntity(Integer estrelas, Salas sala) {
        Avaliacao entity = new Avaliacao();
        entity.setQuantEstrelas(estrelas);
        entity.setSala(sala);
        return entity;
    }

    public AvaliacaoResponse toResponse(Avaliacao entity) {
        return new AvaliacaoResponse(
                entity.getId(),
                entity.getSala().getSalasId(),
                entity.getQuantEstrelas());
    }
}
