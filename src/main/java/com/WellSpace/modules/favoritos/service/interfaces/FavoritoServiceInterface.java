package com.WellSpace.modules.favoritos.service.interfaces;

import com.WellSpace.modules.favoritos.DTO.FavoritoRequest;
import com.WellSpace.modules.favoritos.DTO.FavoritoResponse;

import java.util.List;
import java.util.UUID;

public interface FavoritoServiceInterface {

    FavoritoResponse criarFavorito(FavoritoRequest request);
    void deletarFavorito(UUID favoritoId);
    List<FavoritoResponse> listarFavoritosPorUsuario(UUID usuarioId);
}
