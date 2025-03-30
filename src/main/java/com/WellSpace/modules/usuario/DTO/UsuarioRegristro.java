package com.WellSpace.modules.usuario.DTO;

import com.WellSpace.modules.usuario.domain.ENUM.UsuarioRole;
import com.WellSpace.utils.validations.MaiorDe18;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioRegristro(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 10, message = "O nome deve ter no mínimo 10 caracteres")
        @Schema(description = "Nome completo do usuário", example = "João Silva", required = true)
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail deve ser válido")
        @Schema(description = "E-mail do usuário", example = "joao.silva@example.com", required = true)
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        @Schema(description = "Senha de acesso do usuário", example = "Senha1234", required = true)
        String senha,

        @Schema(description = "URL da foto de perfil do usuário", example = "https://example.com/foto.jpg")
        String fotoPerfil,

        @NotNull(message = "A integridade é obrigatória")
        @Schema(description = "Estado de integridade do usuário", example = "true", required = true)
        Boolean integridade,

        @NotNull(message = "A data de nascimento é obrigatória")
        @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
        @MaiorDe18(message = "O usuário deve ser maior de 18 anos")
        @Schema(description = "Data de nascimento do usuário", example = "2000-05-15", required = true)
        LocalDate dataNascimento,

        @NotNull(message = "O papel de usuário é obrigatório")
        @Schema(description = "Papel de usuário (role) do usuário", example = "LOCATARIO", required = true)
        UsuarioRole usuarioRole
) {
}
