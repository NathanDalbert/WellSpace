package com.WellSpace.modules.usuario.services;

import com.WellSpace.Security.TokenService;
import com.WellSpace.modules.usuario.DTO.UsuarioLogin;
import com.WellSpace.modules.usuario.DTO.UsuarioRegristro;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.domain.ENUM.UsuarioRole;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
import com.WellSpace.modules.usuario.services.mappers.UsuarioMapper;
import com.WellSpace.modules.usuario.exceptions.UsuarioJaCadastradoException;
import com.WellSpace.modules.usuario.exceptions.SenhaIncorretaException;
import com.WellSpace.modules.usuario.exceptions.UsuarioNaoEncontradoException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public String registrarUsuario(@Valid UsuarioRegristro usuarioRegristro) {
        if (usuarioRepository.existsByEmail(usuarioRegristro.email())) {
            throw new UsuarioJaCadastradoException("Email já cadastrado");
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioRegristro);

        if (usuarioRegristro.usuarioRole() == null) {
            usuario.setUsuarioRole(UsuarioRole.ADMIN);
        } else {
            usuario.setUsuarioRole(usuarioRegristro.usuarioRole());
        }
        usuario.setEmail(usuarioRegristro.email());
        usuario.setSenha(passwordEncoder.encode(usuarioRegristro.senha()));

        usuarioRepository.save(usuario);
        return tokenService.generateToken(usuario);
    }

    public String login(UsuarioLogin usuarioLogin) {
        Usuario usuario = usuarioRepository.findByEmail(usuarioLogin.email())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (!passwordEncoder.matches(usuarioLogin.senha(), usuario.getSenha())) {
            throw new SenhaIncorretaException("Senha incorreta");
        }

        return tokenService.generateToken(usuario);
    }
}
