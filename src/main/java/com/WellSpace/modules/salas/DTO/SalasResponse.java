package com.WellSpace.modules.salas.DTO;

import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;

import java.time.LocalTime;
import java.util.UUID;

public record SalasResponse(

        UUID salasId,
        String nomeSala,
        String descricao,
        String tamanho,
        Float precoHora,
        String disponibilidadeDiaSemana,
        LocalTime disponibilidadeInicio,
        LocalTime disponibilidadeFim,
        DisponibilidadeSalaEnum disponibilidadeSala) {
}
