package com.WellSpace.modules.usuario.controller;

import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.DTO.UsuarioUpdateRequest;
import com.WellSpace.modules.usuario.services.UsuarioService;
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
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/buscar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LOCATARIO')")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(@RequestBody @Valid UsuarioUpdateRequest usuarioUpdateRequest) {
        try {

            UUID usuarioId = usuarioUpdateRequest.usuarioId();
            UsuarioResponse usuarioResponse = usuarioService.buscarUsuarioPorId(usuarioId);
            return ResponseEntity.ok(usuarioResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UsuarioResponse(
                    null, "Erro: " + e.getMessage(), null, null, null, null));
        }
    }


    @PutMapping("/atualizar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LOCATARIO')")
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

    @DeleteMapping("/deletar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LOCATARIO')")
    public ResponseEntity<String> deletarUsuario(@RequestBody @Valid UsuarioUpdateRequest usuarioUpdateRequest) {
        try {
            UUID usuarioId = usuarioUpdateRequest.usuarioId();
            usuarioService.deletarUsuario(usuarioId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar o usuário: " + e.getMessage());
        }
    }
}
