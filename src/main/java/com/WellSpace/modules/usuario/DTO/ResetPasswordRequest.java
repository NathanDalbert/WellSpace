package com.WellSpace.modules.usuario.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

public record ResetPasswordRequest(
        @NotBlank
        @Schema(description = "Token recebido por e-mail", required = true)
        String token,

        @NotBlank
        @Size(min = 8)
        @Schema(description = "Nova senha para o usuário", example = "NovaSenha@123", required = true)
        String newPassword
) {}