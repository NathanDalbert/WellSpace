package com.WellSpace.modules.salas.service.Mapper;

import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SalasMapper {

    @Mapping(source = "salasId", target = "salaID")
    @Mapping(source = "nomeSala", target = "nomeSala")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "localizacao", target = "localizacao")
    @Mapping(source = "locador", target = "locador")
    @Mapping(source = "disponibilidadeDiaSemana", target = "disponibilidadeDiaSemana")
    @Mapping(source = "disponibilidadeInicio", target = "horaInicio")
    @Mapping(source = "disponibilidadeFim", target = "horaFim")
    @Mapping(source = "disponibilidadeSala", target = "disponibilidade")
    SalasResponse toSalasResponse(Salas salas);

    @Mapping(source = "nomeSala", target = "nomeSala")
    @Mapping(source = "descricao", target = "descricao")
    @Mapping(source = "tamanho", target = "tamanho")
    @Mapping(source = "precoHora", target = "precoHora")
    @Mapping(source = "disponibilidadeDiaSemana", target = "disponibilidadeDiaSemana")
    @Mapping(source = "horaInicio", target = "disponibilidadeInicio")
    @Mapping(source = "horaFim", target = "disponibilidadeFim")
    @Mapping(source = "disponibilidadeSala", target = "disponibilidadeSala")
    Salas toSalas(SalasRequest salasRequest);
}
