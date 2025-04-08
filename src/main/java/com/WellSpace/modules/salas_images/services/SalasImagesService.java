package com.WellSpace.modules.salas_images.services;

import com.WellSpace.modules.salas_images.DTO.SalasImagesRequest;
import com.WellSpace.modules.salas_images.domain.SalasImages;
import com.WellSpace.modules.salas_images.services.mappers.SalasImagesMapper;
import com.WellSpace.modules.salas_images.repository.SalasImagesRepository;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.salas.repository.SalasRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalasImagesService {

    private final SalasImagesRepository repository;

    private final SalasRepository salasRepository;

    private final Cloudinary cloudinary;

    public SalasImages salvarImagem(UUID salasId, SalasImagesRequest request) throws IOException {
        MultipartFile file = request.imagem();
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String url = uploadResult.get("secure_url").toString();

        Salas sala = salasRepository.findById(salasId)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        SalasImages entity = SalasImagesMapper.toEntity(url, sala);
        return repository.save(entity);
    }
}
