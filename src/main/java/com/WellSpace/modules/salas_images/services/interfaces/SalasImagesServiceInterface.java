package com.WellSpace.modules.salas_images.services.interfaces;

import com.WellSpace.modules.salas_images.DTO.SalasImagesRequest;
import com.WellSpace.modules.salas_images.DTO.SalasImagesResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface SalasImagesServiceInterface {

    SalasImagesResponse salvarImagem(UUID salasId, SalasImagesRequest request) throws IOException;

    List<SalasImagesResponse> listarImagensPorSala(UUID salaId);

    SalasImagesResponse buscarPorId(UUID id);

    SalasImagesResponse atualizarImagem(UUID id, MultipartFile novaImagem) throws IOException;

    void deletarImagem(UUID id);
}
