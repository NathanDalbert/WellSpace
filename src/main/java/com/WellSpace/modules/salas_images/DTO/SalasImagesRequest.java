package com.WellSpace.modules.salas_images.DTO;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public record SalasImagesRequest(UUID salasId, MultipartFile imagem) {

}
