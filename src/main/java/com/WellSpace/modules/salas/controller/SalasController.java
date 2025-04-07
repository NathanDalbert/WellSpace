package com.WellSpace.modules.salas.controller;

import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;
import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import com.WellSpace.modules.salas.service.interfaces.SalasServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/salas")
@RequiredArgsConstructor
public class SalasController {

    private final SalasServiceInterface salasServiceInterface;

    @PostMapping("/criar-sala")
    public ResponseEntity<SalasResponse> criarSala(@RequestBody @Valid SalasRequest salasRequest) {
        try {
            SalasResponse response = salasServiceInterface.criarSala(salasRequest);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/listar-salas")
    public ResponseEntity<List<SalasResponse>> listarSalas() {
        try {
            List<SalasResponse> salas = salasServiceInterface.listarSalas();
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/listar-salas/disponibilidade/{disponibilidadeSala}")
    public ResponseEntity<List<SalasResponse>> listarSalasPorDisponibilidade(
            @PathVariable DisponibilidadeSalaEnum disponibilidadeSala) {
        try {
            List<SalasResponse> salas = salasServiceInterface.listarSalasPorDisponibilidade(disponibilidadeSala);
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/buscar-salas/horario")
    public ResponseEntity<List<SalasResponse>> buscarSalasPorHorario(
            @RequestParam @Valid LocalTime inicio, @RequestParam @Valid LocalTime fim) {
        try {
            List<SalasResponse> salas = salasServiceInterface.buscarSalasPorHorario(inicio, fim);
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/alterar-disponibilidade/{id}")
    public ResponseEntity<SalasResponse> alterarDisponibilidade(
            @PathVariable UUID id, @RequestBody @Valid DisponibilidadeSalaEnum disponibilidadeSala) {
        try {
            SalasResponse response = salasServiceInterface.alterarDisponibilidade(id, disponibilidadeSala);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/deletar-sala/{id}")
    public ResponseEntity<String> deletarSala(@PathVariable UUID id) {
        try {
            salasServiceInterface.deletarSala(id);
            return ResponseEntity.status(200).body("Sala deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Sala não encontrada.");
        }
    }
}
