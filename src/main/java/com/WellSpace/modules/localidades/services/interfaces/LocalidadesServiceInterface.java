package com.WellSpace.modules.localidades.services.interfaces;

import com.WellSpace.modules.localidades.DTO.LocalidadesRequest;
import com.WellSpace.modules.localidades.DTO.LocalidadesResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocalidadesServiceInterface {

    LocalidadesResponse createLocalidade(LocalidadesRequest requestDTO, UUID usuario);

    List<LocalidadesResponse> getAllLocalidades();

    Optional<LocalidadesResponse> getLocalidadeById(UUID localidadeId);
}
