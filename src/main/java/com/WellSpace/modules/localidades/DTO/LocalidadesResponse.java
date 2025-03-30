package com.WellSpace.modules.localidades.DTO;

import java.util.UUID;

public record LocalidadesResponse(
        UUID localidade_id,
        String nome_local,
        String descricao,
        String localizacao,
        UUID locador
) {
}
