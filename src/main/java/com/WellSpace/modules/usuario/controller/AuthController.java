package com.WellSpace.modules.usuario.controller;

import com.WellSpace.modules.usuario.DTO.UsuarioLogin;
import com.WellSpace.modules.usuario.DTO.UsuarioRegristro;
import com.WellSpace.modules.usuario.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registrar usuário", description = "Registra um novo usuário e retorna um token de autenticação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid UsuarioRegristro usuarioRegristro) {
        try {
            String token = authService.registrarUsuario(usuarioRegristro);
            return ResponseEntity.ok("Usuário registrado com sucesso. Token: " + token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar o usuário: " + e.getMessage());
        }
    }

    @Operation(summary = "Login do usuário", description = "Realiza o login do usuário e retorna um token de autenticação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Login bem-sucedido"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody @Valid UsuarioLogin usuarioLogin) {
        try {
            String token = authService.login(usuarioLogin);
            return ResponseEntity.ok("Login bem-sucedido! Token: " + token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciais inválidas: " + e.getMessage());
        }
    }
}
