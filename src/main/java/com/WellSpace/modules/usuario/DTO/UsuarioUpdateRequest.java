package com.WellSpace.modules.usuario.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 10, message = "O nome deve ter no mínimo 10 caracteres")
        String nome,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
        String senha,

        String fotoPerfil
) {

}
