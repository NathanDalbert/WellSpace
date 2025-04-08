package com.WellSpace.modules.salas_images.services.interfaces;

import java.util.UUID;

import com.WellSpace.modules.salas_images.DTO.SalasImagesRequest;
import com.WellSpace.modules.salas_images.domain.SalasImages;

public interface SalasImagesServiceInterface {
    public SalasImages salvarImagem(UUID salasId, SalasImagesRequest request);

}
