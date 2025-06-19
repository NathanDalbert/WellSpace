package com.WellSpace.modules.favoritos.service.Mapper;

import com.WellSpace.modules.favoritos.DTO.FavoritoResponse;
import com.WellSpace.modules.favoritos.domain.Favorito;

public class FavoritoMapper {

    public static FavoritoResponse toResponse(Favorito favorito) {
        return new FavoritoResponse(
                favorito.getFavoritoId(),
                favorito.getUsuario().getUsuarioId(),
                favorito.getSala().getSalasId()
        );
    }
}
