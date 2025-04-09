package com.WellSpace.modules.reservas.services.interfaces;

import com.WellSpace.modules.reservas.DTO.ReservaRequest;
import com.WellSpace.modules.reservas.DTO.ReservaResponse;

import java.util.List;
import java.util.UUID;

public interface ReservaServiceInterface {

    ReservaResponse criarReserva(ReservaRequest request);

    ReservaResponse buscarPorId(UUID id);

    List<ReservaResponse> listarTodas();

    void deletarReserva(UUID id);
}
