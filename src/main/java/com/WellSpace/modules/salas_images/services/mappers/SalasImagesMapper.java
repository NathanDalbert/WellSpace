package com.WellSpace.modules.salas_images.services.mappers;

import com.WellSpace.modules.salas_images.domain.SalasImages;
import com.WellSpace.modules.salas.domain.Salas;

public class SalasImagesMapper {

    public static SalasImages toEntity(String imageUrl, Salas sala) {
        SalasImages entity = new SalasImages();
        entity.setImagem(imageUrl);
        entity.setSala(sala);
        return entity;
    }
}
