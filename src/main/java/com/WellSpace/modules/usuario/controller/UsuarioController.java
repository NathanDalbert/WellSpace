package com.WellSpace.modules.usuario.controller;

import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.DTO.UsuarioUpdateRequest;
import com.WellSpace.modules.usuario.services.interfaces.UsuarioServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
@Tag(name = "Usuario", description = "Operações relacionadas ao gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioServiceInterface usuarioService;

    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LOCATARIO')")
    @Operation(summary = "Buscar usuário por ID", description = "Busca um usuário pelo seu ID. Somente ADMIN e LOCATARIO podem acessar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(@PathVariable UUID id) {
        try {
            UsuarioResponse usuarioResponse = usuarioService.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuarioResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UsuarioResponse(
                    null, "Erro: " + e.getMessage(), null, null, null, null));
        }
    }

    @PutMapping("/atualizar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LOCATARIO')")
    @Operation(summary = "Atualizar dados do usuário", description = "Atualiza os dados de um usuário pelo seu ID. Somente ADMIN e LOCATARIO podem acessar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@RequestBody @Valid UsuarioUpdateRequest usuarioUpdateRequest) {
        try {
            UUID usuarioId = usuarioUpdateRequest.usuarioId();
            UsuarioResponse usuarioResponse = usuarioService.atualizarUsuario(usuarioId, usuarioUpdateRequest);
            return ResponseEntity.ok(usuarioResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UsuarioResponse(
                    null, "Erro: " + e.getMessage(), null, null, null, null));
        }
    }

    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LOCATARIO')")
    @Operation(summary = "Deletar usuário", description = "Deleta um usuário pelo seu ID. Somente ADMIN e LOCATARIO podem acessar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<String> deletarUsuario(@PathVariable UUID id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar o usuário: " + e.getMessage());
        }
    }
}
