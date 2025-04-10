package com.WellSpace.modules.usuario.services;

import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.DTO.UsuarioUpdateRequest;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
import com.WellSpace.modules.usuario.services.interfaces.UsuarioServiceInterface;
import com.WellSpace.modules.usuario.services.mappers.UsuarioMapper;
import com.WellSpace.modules.usuario.exceptions.UsuarioNaoEncontradoException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioService implements UsuarioServiceInterface {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponse buscarUsuarioPorId(UUID usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    public UsuarioResponse atualizarUsuario(UUID usuarioId, UsuarioUpdateRequest usuarioUpdateRequest) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

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
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        usuarioRepository.delete(usuario);
    }
    @Override
    public List<UsuarioResponse> buscarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }
}

