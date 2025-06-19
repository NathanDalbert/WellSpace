package com.WellSpace.modules.usuario.services;

import com.WellSpace.Security.TokenService;
import com.WellSpace.modules.usuario.DTO.UsuarioLogin;
import com.WellSpace.modules.usuario.DTO.UsuarioRegristro;
import com.WellSpace.modules.usuario.domain.PasswordResetToken;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.domain.ENUM.UsuarioRole;
import com.WellSpace.modules.usuario.exceptions.*;
import com.WellSpace.modules.usuario.repository.PasswordResetTokenRepository;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
import com.WellSpace.modules.usuario.services.mappers.UsuarioMapper;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;
    private final Cloudinary cloudinary;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

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
            usuario.setUsuarioRole(UsuarioRole.LOCATARIO);
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

    public void initiatePasswordResetProcess(String email) {
        Optional<Usuario> userOptional = usuarioRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            Usuario usuario = userOptional.get();

            Optional<PasswordResetToken> existingTokenOpt = passwordResetTokenRepository.findByUsuario(usuario);
            existingTokenOpt.ifPresent(token -> {
                passwordResetTokenRepository.delete(token);
                passwordResetTokenRepository.flush();
            });

            String tokenValue = UUID.randomUUID().toString();
            PasswordResetToken newPasswordResetToken = new PasswordResetToken(tokenValue, usuario);
            passwordResetTokenRepository.save(newPasswordResetToken);

            emailService.sendPasswordResetEmail(usuario.getEmail(), tokenValue);
        }
    }

    public void finalizePasswordReset(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Token de redefinição inválido ou não encontrado."));

        if (resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken);
            throw new TokenExpiredException("Seu token de redefinição de senha expirou. Por favor, solicite um novo.");
        }

        Usuario usuario = resetToken.getUsuario();
        usuario.setSenha(passwordEncoder.encode(newPassword));
        usuarioRepository.save(usuario);
        passwordResetTokenRepository.delete(resetToken);
    }
}