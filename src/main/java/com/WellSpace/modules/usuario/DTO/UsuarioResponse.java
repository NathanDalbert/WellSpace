package com.WellSpace.modules.usuario.DTO;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public record UsuarioResponse(
        UUID usuarioId,
        String nome,
        String email,
        Optional<String> fotoPerfil,
        Optional<Boolean> integridade,
        Optional<LocalDate> dataNascimento
     ) {}
