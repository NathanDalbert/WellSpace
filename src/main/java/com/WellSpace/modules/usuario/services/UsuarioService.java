package com.WellSpace.modules.usuario.services;

import com.WellSpace.Security.TokenService;
import com.WellSpace.modules.usuario.DTO.UsuarioRequest;
import com.WellSpace.modules.usuario.DTO.UsuarioResponse;
import com.WellSpace.modules.usuario.DTO.UsuarioUpdateRequest;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
import com.WellSpace.modules.usuario.services.interfaces.UsuarioServiceInterface;
import com.WellSpace.modules.usuario.services.mappers.UsuarioMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UsuarioService implements UsuarioServiceInterface {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final TokenService tokenService;


    @Override
    public UsuarioResponse buscarUsuarioPorId(UUID usuarioId) {

        String userIdFromToken = extrairIdDoToken();


        if (userIdFromToken != null && !userIdFromToken.equals(usuarioId.toString())) {
            throw new RuntimeException("Você não tem permissão para acessar esse usuário.");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    public UsuarioResponse atualizarUsuario(UUID usuarioId, UsuarioUpdateRequest usuarioUpdateRequest) {

        String userIdFromToken = extrairIdDoToken();


        if (!userIdFromToken.equals(usuarioId.toString())) {
            throw new RuntimeException("Você não tem permissão para atualizar este usuário.");
        }

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

        String userIdFromToken = extrairIdDoToken();

        if (!userIdFromToken.equals(usuarioId.toString())) {
            throw new RuntimeException("Você não tem permissão para excluir este usuário.");
        }

        usuarioRepository.deleteById(usuarioId);
    }


    private String extrairIdDoToken() {

        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();  // Aqui o token é extraído do contexto de segurança
        return tokenService.decodeToken(token);  // Utilizando seu método decodeToken do TokenService
    }
}
