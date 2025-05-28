package com.WellSpace.modules.usuario.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Transactional
@RequiredArgsConstructor
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);


    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.reset-url}")
    private String frontendResetUrl;

    public void sendPasswordResetEmail(String toEmail, String token) {
        String resetLink = frontendResetUrl + "?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("WellSpace - Redefinição de Senha");
        message.setText(
                "Olá,\n\n" +
                        "Você solicitou a redefinição da sua senha para a plataforma WellSpace.\n" +
                        "Para criar uma nova senha, por favor, clique no link abaixo. Este link é válido por 1 hora:\n" +
                        resetLink + "\n\n" +
                        "Se você não fez esta solicitação, pode ignorar este e-mail com segurança.\n\n" +
                        "Atenciosamente,\n" +
                        "Equipe WellSpace"
        );

        try {
            mailSender.send(message);
            logger.info("E-mail de redefinição de senha enviado para: {}", toEmail);
        } catch (Exception e) {
            logger.error("Falha ao enviar e-mail de redefinição de senha para {}: {}", toEmail, e.getMessage(), e);
        }
    }
}