package com.WellSpace.modules.favoritos.controller;

import com.WellSpace.modules.favoritos.DTO.FavoritoRequest;
import com.WellSpace.modules.favoritos.DTO.FavoritoResponse;
import com.WellSpace.modules.favoritos.exceptions.FavoritoJaExisteException;
import com.WellSpace.modules.favoritos.exceptions.FavoritoNaoEncontradoException;
import com.WellSpace.modules.favoritos.service.interfaces.FavoritoServiceInterface;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoServiceInterface favoritoServiceInterface;

    @PostMapping("/criar-favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Favorito criado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados ou favorito já existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<?> criarFavorito(@RequestBody @Valid FavoritoRequest req) {
        try {
            if (req == null) {
                return ResponseEntity.badRequest().body("Requisição não pode ser nula.");
            }
            if (req.usuarioId() == null || req.salaId() == null) {
                return ResponseEntity.badRequest().body("ID do usuário e ID da sala não podem ser nulos.");
            }

            FavoritoResponse resp = favoritoServiceInterface.criarFavorito(req);
            return ResponseEntity.status(201).body(resp);

        } catch (FavoritoJaExisteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro interno ao criar favorito: " + e.getMessage());
        }
    }

    @GetMapping("/listar-por-usuario/{usuarioId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de favoritos do usuário retornada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "ID do usuário inválido ou nulo."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar favoritos")
    })
    public ResponseEntity<?> listarFavoritosPorUsuario(@PathVariable UUID usuarioId) {
        try {
            if (usuarioId == null) {
                return ResponseEntity.badRequest().body("ID do usuário não pode ser nulo.");
            }

            List<FavoritoResponse> favoritos = favoritoServiceInterface.listarFavoritosPorUsuario(usuarioId);
            return ResponseEntity.ok(favoritos);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao listar favoritos por usuário: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{favoritoId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorito deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Favorito não encontrado para exclusão"),
            @ApiResponse(responseCode = "500", description = "Erro interno ao deletar favorito")
    })
    public ResponseEntity<?> deletarFavorito(@PathVariable UUID favoritoId) {
        try {
            if (favoritoId == null) {
                return ResponseEntity.badRequest().body("ID do favorito não pode ser nulo.");
            }

            favoritoServiceInterface.deletarFavorito(favoritoId);
            return ResponseEntity.ok("Favorito deletado com sucesso.");

        } catch (FavoritoNaoEncontradoException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao deletar favorito: " + e.getMessage());
        }
    }
}
