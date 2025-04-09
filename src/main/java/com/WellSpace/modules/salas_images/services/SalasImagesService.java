package com.WellSpace.modules.salas_images.services;

import com.WellSpace.modules.salas_images.DTO.SalasImagesRequest;
import com.WellSpace.modules.salas_images.DTO.SalasImagesResponse;
import com.WellSpace.modules.salas_images.domain.SalasImages;
import com.WellSpace.modules.salas_images.exceptions.SalasImageNotFoundException;
import com.WellSpace.modules.salas_images.repository.SalasImagesRepository;
import com.WellSpace.modules.salas_images.services.interfaces.SalasImagesServiceInterface;
import com.WellSpace.modules.salas_images.services.mappers.SalasImagesMapper;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.salas.repository.SalasRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SalasImagesService implements SalasImagesServiceInterface {

    private final SalasImagesRepository repository;
    private final SalasRepository salasRepository;
    private final SalasImagesMapper mapper;
    private final Cloudinary cloudinary;

    @Override
    public SalasImagesResponse salvarImagem(UUID salasId, SalasImagesRequest request) throws IOException {
        MultipartFile file = request.imagem();
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String url = uploadResult.get("secure_url").toString();

        Salas sala = salasRepository.findById(salasId)
                .orElseThrow(() -> new SalasImageNotFoundException("Sala não encontrada"));

        SalasImages entity = mapper.toEntity(url, sala);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public List<SalasImagesResponse> listarImagensPorSala(UUID salaId) {
        Salas sala = salasRepository.findById(salaId)
                .orElseThrow(() -> new SalasImageNotFoundException("Sala não encontrada"));

        return sala.getImagens()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SalasImagesResponse buscarPorId(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new SalasImageNotFoundException("Imagem não encontrada"));
    }

    @Override
    public SalasImagesResponse atualizarImagem(UUID id, MultipartFile novaImagem) throws IOException {
        SalasImages existente = repository.findById(id)
                .orElseThrow(() -> new SalasImageNotFoundException("Imagem não encontrada"));

        Map uploadResult = cloudinary.uploader().upload(novaImagem.getBytes(), ObjectUtils.emptyMap());
        String novaUrl = uploadResult.get("secure_url").toString();

        existente.setImagem(novaUrl);
        return mapper.toResponse(repository.save(existente));
    }

    @Override
    public void deletarImagem(UUID id) {
        SalasImages imagem = repository.findById(id)
                .orElseThrow(() -> new SalasImageNotFoundException("Imagem não encontrada"));

        repository.delete(imagem);
    }
}
