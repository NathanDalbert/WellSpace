package com.WellSpace.modules.contato.services.mappers;

import com.WellSpace.modules.contato.domain.Contato;
import com.WellSpace.modules.contato.DTO.ContatoRequest;
import com.WellSpace.modules.contato.DTO.ContatoResponse;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContatoMapper {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Contato toEntity(ContatoRequest contatoRequest) {

        Usuario usuario = usuarioRepository.findById(contatoRequest.usuario_id())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        return Contato.newContato(usuario, contatoRequest.contato());
    }

    public ContatoResponse toResponse(Contato contato) {
        return new ContatoResponse(
                contato.getContato_id(),
                contato.getUsuario().getUsuario_id(),
                contato.getContato());
    }

    public void updateEntityFromRequest(ContatoRequest contatoRequest, Contato contato) {

        Usuario usuario = usuarioRepository.findById(contatoRequest.usuario_id())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        contato.setUsuario(usuario);
        contato.setContato(contatoRequest.contato());
    }
}
