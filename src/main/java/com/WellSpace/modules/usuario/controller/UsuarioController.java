package com.WellSpace.modules.usuario.controller;

import com.WellSpace.modules.usuario.DTO.UsuarioRequest;
import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.DTO.UsuarioUpdateRequest;
import com.WellSpace.modules.usuario.services.UsuarioService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;




    @PostMapping("/buscar")
    public ResponseEntity<UsuarioResponse> buscarUsuarioPorId(@Valid @RequestBody UUID usuarioId) {
        try {
            UsuarioResponse usuarioResponse = usuarioService.buscarUsuarioPorId(usuarioId);
            return ResponseEntity.ok(usuarioResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UsuarioResponse(
                    null, "Erro", null, null, null, null));
        }
    }


    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@RequestParam UUID usuarioId, @Valid @RequestBody UsuarioUpdateRequest usuarioUpdateRequest) {


        try {

            UsuarioResponse usuarioResponse = usuarioService.atualizarUsuario(usuarioId, usuarioUpdateRequest);
            return ResponseEntity.ok(usuarioResponse);
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UsuarioResponse(
                    null, "Erro", null, null, null, null));
        }
    }


    @PostMapping("/deletar")
    public ResponseEntity<String> deletarUsuario(@Valid @RequestBody UUID usuarioId) {
        try {
            usuarioService.deletarUsuario(usuarioId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
    }
}
