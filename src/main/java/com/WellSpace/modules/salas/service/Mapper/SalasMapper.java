package com.WellSpace.modules.salas.service.Mapper;

import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.repository.UsuarioRepository; // Supondo que você tenha um repositório de Usuario
import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalasMapper {

    private final UsuarioRepository usuarioRepository;


    public Salas toEntity(@Valid SalasRequest salasRequest) {

        Usuario usuario = usuarioRepository.findById(salasRequest.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Salas sala = new Salas(
                salasRequest.nomeSala(),
                salasRequest.descricao(),
                salasRequest.tamanho(),
                salasRequest.precoHora(),
                salasRequest.disponibilidadeDiaSemana(),
                salasRequest.disponibilidadeInicio(),
                salasRequest.disponibilidadeFim(),
                salasRequest.disponibilidadeSala()
        );


        sala.setUsuario(usuario);

        return sala;
    }

    public SalasResponse toResponse(Salas salas) {
        return new SalasResponse(
                salas.getSalasId(),
                salas.getNomeSala(),
                salas.getDescricao(),
                salas.getTamanho(),
                salas.getPrecoHora(),
                salas.getDisponibilidadeDiaSemana(),
                salas.getDisponibilidadeInicio(),
                salas.getDisponibilidadeFim(),
                salas.getDisponibilidadeSala(),
                salas.getUsuario().getUsuarioId()
        );
    }
}
