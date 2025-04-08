package com.WellSpace.modules.salas_images.controller;

import com.WellSpace.modules.salas_images.DTO.SalasImagesRequest;
import com.WellSpace.modules.salas_images.domain.SalasImages;
import com.WellSpace.modules.salas_images.services.SalasImagesService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/imagens")
@RequiredArgsConstructor
public class SalasImagesController {

    private final SalasImagesService service;

    @PostMapping("/upload")
    public ResponseEntity<SalasImages> uploadImagem(@PathVariable UUID salasId,
            @RequestParam("imagem") MultipartFile imagem) {
        try {
            SalasImages saved = service.salvarImagem(salasId, new SalasImagesRequest(salasId, imagem));
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
