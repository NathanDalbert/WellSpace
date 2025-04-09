package com.WellSpace.modules.reservas.services.mapper;


import com.WellSpace.modules.reservas.DTO.ReservaRequest;
import com.WellSpace.modules.reservas.DTO.ReservaResponse;
import com.WellSpace.modules.reservas.domain.ENUM.StatusEnum;
import com.WellSpace.modules.reservas.domain.Reserva;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.usuario.domain.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public Reserva toEntity(ReservaRequest request, Salas salas, Usuario locatario, Usuario locador) {
        Reserva reserva = Reserva.newReserva(
                request.tempoFim(),
                request.tempoInicio(),
                request.dataReserva(),
                StatusEnum.valueOf(request.status())
        );
        reserva.setSalas(salas);
        reserva.setLocatario(locatario);
        reserva.setLocador(locador);
        return reserva;
    }

    public ReservaResponse toResponseDTO(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getSalas().getSalasId(),
                reserva.getLocatario().getUsuarioId(),
                reserva.getLocador().getUsuarioId(),
                reserva.getTempoInicio(),
                reserva.getTempoFim(),
                reserva.getDataReserva(),
                reserva.getStatus().name().toLowerCase()
        );
    }
}
