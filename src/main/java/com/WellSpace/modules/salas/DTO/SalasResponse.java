package com.WellSpace.modules.salas.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record SalasResponse(UUID salaID,
                            String nomeSala,
                            String descricao,

                            String localizacao,
                            UUID locador,
                            LocalDateTime horaInicio,
                            LocalDateTime hoeraFim
                            ) {
}
