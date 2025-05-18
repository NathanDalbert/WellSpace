package com.WellSpace.modules.salas.service.Mapper;

import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;
import com.WellSpace.modules.salas.domain.Salas;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class SalasMapper {


    public Salas toEntity(@Valid SalasRequest req) {
        return Salas.newSala(
                req.nomeSala(),
                req.descricao(),
                req.tamanho(),
                req.precoHora(),
                req.disponibilidadeDiaSemana(),
                req.disponibilidadeInicio(),
                req.disponibilidadeFim(),
                req.disponibilidadeSala()
        );
    }

    public SalasResponse toResponse(Salas sala) {
        return new SalasResponse(
                sala.getSalasId(),
                sala.getNomeSala(),
                sala.getDescricao(),
                sala.getTamanho(),
                sala.getPrecoHora(),
                sala.getDisponibilidadeDiaSemana(),
                sala.getDisponibilidadeInicio(),
                sala.getDisponibilidadeFim(),
                sala.getDisponibilidadeSala(),
                sala.getUsuario().getUsuarioId()
        );
    }
}