package com.WellSpace.modules.salas.controller;

import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;
import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import com.WellSpace.modules.salas.service.interfaces.SalasServiceInterface;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Sala criada com sucesso!"), @ApiResponse(responseCode = "400", description = "Erro na validação dos dados de sala"), @ApiResponse(responseCode = "500", description = "Erro interno do servidor")}


    )
    public ResponseEntity<SalasResponse> criarSala(@RequestBody @Valid SalasRequest salasRequest) {
        try {
            SalasResponse response = salasServiceInterface.criarSala(salasRequest);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/listar-salas")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de salas retornada com sucesso!"), @ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public ResponseEntity<List<SalasResponse>> listarSalas() {
        try {
            List<SalasResponse> salas = salasServiceInterface.listarSalas();
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/listar-salas/disponibilidade/{disponibilidadeSala}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de salas por disponibilidade retornada com sucesso!"), @ApiResponse(responseCode = "400", description = "Valor inválido para disponibilidade de sala "),@ApiResponse(responseCode = "500", description = "Erro interno do servidor")})
    public ResponseEntity<List<SalasResponse>> listarSalasPorDisponibilidade(@PathVariable DisponibilidadeSalaEnum disponibilidadeSala) {
        try {
            List<SalasResponse> salas = salasServiceInterface.listarSalasPorDisponibilidade(disponibilidadeSala);
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/buscar-salas/horario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de salas retornada com sucesso para o intervalo de horário fornecido"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos horários de busca"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar salas por horário")
    })
    public ResponseEntity<List<SalasResponse>> buscarSalasPorHorario(@RequestParam @Valid LocalTime inicio, @RequestParam @Valid LocalTime fim) {
        try {
            List<SalasResponse> salas = salasServiceInterface.buscarSalasPorHorario(inicio, fim);
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/alterar-disponibilidade/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidade da sala alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor inválido para alteração da disponibilidade"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada para alteração"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao alterar disponibilidade")
    })
    public ResponseEntity<SalasResponse> alterarDisponibilidade(@PathVariable UUID id, @RequestBody @Valid DisponibilidadeSalaEnum disponibilidadeSala) {
        try {
            SalasResponse response = salasServiceInterface.alterarDisponibilidade(id, disponibilidadeSala);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/deletar-sala/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada para exclusão"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao deletar sala")
    })
    public ResponseEntity<String> deletarSala(@PathVariable UUID id) {
        try {
            salasServiceInterface.deletarSala(id);
            return ResponseEntity.status(200).body("Sala deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Sala não encontrada.");
        }
    }
}
