package com.WellSpace.modules.usuario.services;

import com.WellSpace.modules.usuario.DTO.UsuarioRequest;
import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.DTO.UsuarioUpdateRequest;  // Novo DTO para atualização
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
import com.WellSpace.modules.usuario.services.interfaces.UsuarioServiceInterface;
import com.WellSpace.modules.usuario.services.mappers.UsuarioMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UsuarioService implements UsuarioServiceInterface {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponse criarUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);
        usuarioRepository.save(usuario);
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    public UsuarioResponse buscarUsuarioPorId(UUID usuarioId) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            return usuarioMapper.toResponse(usuario);
        } else {
            throw new RuntimeException("Usuario não encontrado");
        }
    }

    @Override
    public UsuarioResponse atualizarUsuario(UUID usuarioId, UsuarioUpdateRequest usuarioUpdateRequest) {
        // Busca o usuário no banco de dados
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        if (usuarioUpdateRequest.nome() != null) {
            usuario.setNome(usuarioUpdateRequest.nome());
        }
        if (usuarioUpdateRequest.senha() != null) {
            usuario.setSenha(usuarioUpdateRequest.senha());
        }
        if (usuarioUpdateRequest.fotoPerfil() != null) {
            usuario.setFotoPerfil(usuarioUpdateRequest.fotoPerfil());
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);


        return usuarioMapper.toResponse(usuarioAtualizado);
    }

    @Override
    public void deletarUsuario(UUID usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }
}
