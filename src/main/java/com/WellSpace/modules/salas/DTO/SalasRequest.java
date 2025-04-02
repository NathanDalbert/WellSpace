package com.WellSpace.modules.salas.DTO;

import java.time.LocalDateTime;

public record SalasRequest(String nomeSala, String descricao, String tamanho, Float precoHora, LocalDateTime horaInicio, LocalDateTime horaFim
     ) {



}
