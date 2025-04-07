package com.WellSpace.modules.salas.DTO;

import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalTime;
import java.util.UUID;

public record SalasResponse(

        @NotNull(message = "O ID da sala não pode ser nulo")
        @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "ID único da sala")
        UUID salasId,

        @NotBlank(message = "O nome da sala não pode estar vazio")
        @Schema(example = "Sala 1", description = "Nome da sala")
        String nomeSala,

        @NotBlank(message = "A descrição não pode estar vazia")
        @Schema(example = "Sala de conferências", description = "Descrição da sala")
        String descricao,

        @NotBlank(message = "O tamanho não pode estar vazio")
        @Schema(example = "Grande", description = "Tamanho da sala")
        String tamanho,

        @Positive(message = "O preço por hora deve ser um valor positivo")
        @Schema(example = "100.0", description = "Preço por hora da sala")
        Float precoHora,

        @NotBlank(message = "A disponibilidade durante a semana não pode estar vazia")
        @Schema(example = "Segunda a Sexta", description = "Disponibilidade durante a semana da sala")
        String disponibilidadeDiaSemana,

        @NotNull(message = "O horário de início não pode ser nulo")
        @Schema(example = "08:00:00", description = "Horário de início da disponibilidade da sala")
        LocalTime disponibilidadeInicio,

        @NotNull(message = "O horário de fim não pode ser nulo")
        @Schema(example = "18:00:00", description = "Horário de fim da disponibilidade da sala")
        LocalTime disponibilidadeFim,

        @NotNull(message = "A disponibilidade da sala deve ser informada")
        @Schema(example = "DISPONIVEL", description = "Estado da disponibilidade da sala")
        DisponibilidadeSalaEnum disponibilidadeSala) {

}
