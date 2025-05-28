package com.WellSpace.modules.usuario.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public record ForgotPasswordRequest(
        @NotBlank
        @Email
        @Schema(description = "E-mail do usuário para redefinição de senha", example = "usuario@example.com", required = true)
        String email
) {}