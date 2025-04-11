package com.WellSpace.modules.salas.service.interfaces;


import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;
import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Component
public interface SalasServiceInterface {

    SalasResponse criarSala(SalasRequest salasRequest, UUID usuarioId);

    List<SalasResponse> listarSalas();
    List<SalasResponse> listarSalasPorDisponibilidade(DisponibilidadeSalaEnum disponibilidadeSala);
    List<SalasResponse> buscarSalasPorHorario(LocalTime inicio, LocalTime fim);
    void deletarSala(UUID id);
    SalasResponse alterarDisponibilidade(UUID id, DisponibilidadeSalaEnum disponibilidadeSala);
}
