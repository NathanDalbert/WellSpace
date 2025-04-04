package com.WellSpace.modules.salas.service.Mapper;

import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SalasMapper {

    public Salas toEntity(@Valid SalasRequest salasRequest) {
        return new Salas(
                salasRequest.nomeSala(),
                salasRequest.descricao(),
                salasRequest.tamanho(),
                salasRequest.precoHora(),
                salasRequest.disponibilidadeDiaSemana(),
                salasRequest.disponibilidadeInicio(),
                salasRequest.disponibilidadeFim(),
                salasRequest.disponibilidadeSala()
        );
    }

    public SalasResponse toResponse(Salas salas) {
        return new SalasResponse(
                salas.getSalasId(),
                salas.getNomeSala(),
                salas.getDescricao(),
                salas.getTamanho(),
                salas.getPrecoHora(),
                salas.getDisponibilidadeDiaSemana(),
                salas.getDisponibilidadeInicio(),
                salas.getDisponibilidadeFim(),
                salas.getDisponibilidadeSala()
        );
    }
}
