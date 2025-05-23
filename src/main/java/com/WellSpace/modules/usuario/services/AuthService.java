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
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;
    private final Cloudinary cloudinary;


    public String registrarUsuario(@Valid UsuarioRegristro usuarioRegristro) {
        if (usuarioRepository.existsByEmail(usuarioRegristro.email())) {
            throw new UsuarioJaCadastradoException("Email já cadastrado");
        }

        String fotoUrl;
        try {
            Map uploadResult = cloudinary.uploader().upload(usuarioRegristro.fotoPerfil().getBytes(), ObjectUtils.emptyMap());
            fotoUrl = uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload da imagem de perfil", e);
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioRegristro, fotoUrl);
        usuario.setSenha(passwordEncoder.encode(usuarioRegristro.senha()));

        if (usuarioRegristro.usuarioRole() == null) {
            usuario.setUsuarioRole(UsuarioRole.LOCADOR);
        } else {
            usuario.setUsuarioRole(usuarioRegristro.usuarioRole());
        }

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
