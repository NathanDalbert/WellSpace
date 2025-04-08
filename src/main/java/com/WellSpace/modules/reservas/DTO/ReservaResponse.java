package com.WellSpace.modules.reservas.DTO;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.UUID;

public record ReservaResponse(
        UUID id,
        UUID salas,
        UUID locatario,
        UUID locador,
        LocalTime tempoInicio,
        LocalTime tempoFim,
        Date dataReserva,
        String status
) {
}
