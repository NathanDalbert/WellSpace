package com.WellSpace.modules.salas.controller;

import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;
import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import com.WellSpace.modules.salas.service.interfaces.SalasServiceInterface;
import com.WellSpace.modules.salas.exceptions.SalaHJaExisteException;
import com.WellSpace.modules.salas.exceptions.SalaNaoEncontradaException;
import com.WellSpace.modules.salas.exceptions.TempoInvalidoException;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sala criada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados de sala"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> criarSala(
            @RequestBody @Valid SalasRequest req
    ) {
        try {
            if (req == null) {
                return ResponseEntity.badRequest().body("Requisição não pode ser nula.");
            }
            if (req.usuarioId() == null) {
                return ResponseEntity.badRequest().body("O ID do usuário não pode ser nulo.");
            }
            SalasResponse resp = salasServiceInterface.criarSala(req, req.usuarioId());
            return ResponseEntity.status(201).body(resp);
        } catch (SalaHJaExisteException | TempoInvalidoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno ao criar sala: " + e.getMessage());
        }
    }

    @GetMapping("/listar-salas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de salas retornada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> listarSalas() {
        try {
            List<SalasResponse> salas = salasServiceInterface.listarSalas();
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao listar salas: " + e.getMessage());
        }
    }

    @GetMapping("/listar-salas/disponibilidade/{disponibilidadeSala}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de salas por disponibilidade retornada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Valor inválido para disponibilidade de sala "),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> listarSalasPorDisponibilidade(@PathVariable DisponibilidadeSalaEnum disponibilidadeSala) {
        try {
            List<SalasResponse> salas = salasServiceInterface.listarSalasPorDisponibilidade(disponibilidadeSala);
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao listar salas por disponibilidade: " + e.getMessage());
        }
    }

    @GetMapping("/buscar-salas/horario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de salas retornada com sucesso para o intervalo de horário fornecido"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos horários de busca"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao buscar salas por horário")
    })
    public ResponseEntity<?> buscarSalasPorHorario(@RequestParam LocalTime inicio, @RequestParam LocalTime fim) {
        try {
            if (inicio == null || fim == null) {
                return ResponseEntity.badRequest().body("Os horários de início e fim não podem ser nulos.");
            }
            List<SalasResponse> salas = salasServiceInterface.buscarSalasPorHorario(inicio, fim);
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao buscar salas por horário: " + e.getMessage());
        }
    }

    @PutMapping("/alterar-disponibilidade/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidade da sala alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Valor inválido para alteração da disponibilidade"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada para alteração"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao alterar disponibilidade")
    })
    public ResponseEntity<?> alterarDisponibilidade(@PathVariable UUID id, @RequestBody DisponibilidadeSalaEnum disponibilidadeSala) {
        try {
            if (id == null || disponibilidadeSala == null) {
                return ResponseEntity.badRequest().body("ID da sala e disponibilidade não podem ser nulos.");
            }
            SalasResponse response = salasServiceInterface.alterarDisponibilidade(id, disponibilidadeSala);
            return ResponseEntity.ok(response);
        } catch (SalaNaoEncontradaException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao alterar disponibilidade: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar-sala/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada para exclusão"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao deletar sala")
    })
    public ResponseEntity<?> deletarSala(@PathVariable UUID id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().body("ID da sala não pode ser nulo.");
            }
            salasServiceInterface.deletarSala(id);
            return ResponseEntity.ok("Sala deletada com sucesso.");
        } catch (SalaNaoEncontradaException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao deletar sala: " + e.getMessage());
        }
    }
}
