package com.WellSpace.modules.usuario.controller;

import com.WellSpace.modules.usuario.DTO.UsuarioLogin;

import com.WellSpace.modules.usuario.DTO.UsuarioRegristro;
import com.WellSpace.modules.usuario.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid UsuarioRegristro usuarioRegristro) {
        String token = authService.registrarUsuario(usuarioRegristro);
        return ResponseEntity.ok("Usuário registrado com sucesso. Token: " + token);
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody UsuarioLogin usuarioLogin) {

        String token = authService.login(usuarioLogin);
        return ResponseEntity.ok("Login bem-sucedido! Token: " + token);
    }
}
