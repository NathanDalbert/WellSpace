package com.WellSpace.modules.avaliacoes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.WellSpace.modules.avaliacoes.DTO.AvaliacaoRequest;
import com.WellSpace.modules.avaliacoes.DTO.AvaliacaoResponse;
import com.WellSpace.modules.avaliacoes.service.AvaliacaoService;

import java.util.UUID;

@RestController
@RequestMapping("/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoService service;

    @PostMapping
    public ResponseEntity<AvaliacaoResponse> avaliar(@RequestBody AvaliacaoRequest request) {
        return ResponseEntity.ok(service.avaliarSala(request));
    }

    @GetMapping("/media/{salaId}")
    public ResponseEntity<Double> mediaEstrelas(@PathVariable UUID salaId) {
        return ResponseEntity.ok(service.obterMediaEstrelasPorSala(salaId));
    }
}
