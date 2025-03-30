package com.WellSpace.modules.localidades.services.mapper;

import com.WellSpace.modules.localidades.DTO.LocalidadesRequest;
import com.WellSpace.modules.localidades.DTO.LocalidadesResponse;
import com.WellSpace.modules.localidades.domain.Localidades;
import com.WellSpace.modules.usuario.domain.Usuario;
import org.springframework.stereotype.Component;

@Component
public class LocalidadesMapper {

    public Localidades toEntity(LocalidadesRequest requestDTO, Usuario usuario) {
        Localidades localidade = Localidades.newLocalidade(
                requestDTO.nome_local(),
                requestDTO.descricao(),
                requestDTO.localizacao()
        );
        localidade.setLocador(usuario);
        return localidade;
    }

    public LocalidadesResponse toResponseDTO(Localidades localidade) {
        return new LocalidadesResponse(
                localidade.getLocalidadeId(),
                localidade.getNomeLocal(),
                localidade.getDescricao(),
                localidade.getLocalizacao(),
                localidade.getLocador() != null ? localidade.getLocador().getUsuario_id() : null
        );
    }
}
