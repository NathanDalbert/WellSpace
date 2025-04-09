package com.WellSpace.modules.salas_images.services.mappers;

import com.WellSpace.modules.salas_images.DTO.SalasImagesResponse;
import com.WellSpace.modules.salas_images.domain.SalasImages;
import com.WellSpace.modules.salas.domain.Salas;
import org.springframework.stereotype.Component;

@Component
public class SalasImagesMapper {

    public SalasImages toEntity(String imageUrl, Salas sala) {
        SalasImages entity = new SalasImages();
        entity.setImagem(imageUrl);
        entity.setSala(sala);
        return entity;
    }

    public SalasImagesResponse toResponse(SalasImages entity) {
        return new SalasImagesResponse(entity.getSalasImagesId(), entity.getImagem());
    }
}
