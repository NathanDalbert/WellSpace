package com.WellSpace.modules.usuario.DTO;

import com.WellSpace.modules.usuario.domain.ENUM.UsuarioRole;
import com.WellSpace.utils.validations.MaiorDe18;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 10, message = "O nome deve ter no mínimo 10 caracteres")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail deve ser válido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String senha,

        String fotoPerfil,

        @NotNull(message = "A integridade é obrigatória")
        Boolean integridade,

        @NotNull(message = "A data de nascimento é obrigatória")
        @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
        @MaiorDe18()
        LocalDate dataNascimento,
        UsuarioRole usuarioRole
) {}
