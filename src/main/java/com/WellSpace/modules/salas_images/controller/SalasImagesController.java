package com.WellSpace.modules.salas_images.controller;

import com.WellSpace.modules.salas_images.DTO.SalasImagesRequest;
import com.WellSpace.modules.salas_images.DTO.SalasImagesResponse;
import com.WellSpace.modules.salas_images.services.SalasImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/imagens")
@RequiredArgsConstructor
public class SalasImagesController {

    private final SalasImagesService service;

    @PostMapping("/upload/{salasId}")
    public ResponseEntity<SalasImagesResponse> uploadImagem(
            @PathVariable UUID salasId,
            @RequestParam("imagem") MultipartFile imagem) {
        try {
            SalasImagesResponse saved = service.salvarImagem(salasId, new SalasImagesRequest(salasId, imagem));
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/sala/{salasId}")
    public ResponseEntity<List<SalasImagesResponse>> listarPorSala(@PathVariable UUID salasId) {
        return ResponseEntity.ok(service.listarImagensPorSala(salasId));
    }

    @GetMapping("/buscar-imagem/{id}")
    public ResponseEntity<SalasImagesResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("atualizar-imagem/{id}")
    public ResponseEntity<SalasImagesResponse> atualizarImagem(
            @PathVariable UUID id,
            @RequestParam("imagem") MultipartFile imagem) {
        try {
            SalasImagesResponse updated = service.atualizarImagem(id, imagem);
            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/deletar-imagem/{id}")
    public ResponseEntity<Void> deletarImagem(@PathVariable UUID id) {
        service.deletarImagem(id);
        return ResponseEntity.noContent().build();
    }
}
