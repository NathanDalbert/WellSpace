package com.WellSpace.modules.reservas.DTO;

import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.UUID;

public record ReservaRequest(
        @NotNull(message = "A sala não pode ser nula.") UUID salas,
        @NotNull(message = "O locatário não pode ser nulo.") UUID locatario,
        @NotNull(message = "O locador não pode ser nulo.") UUID locador,
        @NotNull(message = "O tempo de início não pode ser nulo.") LocalTime tempoInicio,
        @NotNull(message = "O tempo de fim não pode ser nulo.") LocalTime tempoFim,
        @NotNull(message = "A data da reserva não pode ser nula.") Date dataReserva,
        @NotNull(message = "O status da reserva não pode ser nulo.") String status
) {
}
