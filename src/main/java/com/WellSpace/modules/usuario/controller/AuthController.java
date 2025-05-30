package com.WellSpace.modules.usuario.controller;

import com.WellSpace.modules.usuario.DTO.ForgotPasswordRequest;
import com.WellSpace.modules.usuario.DTO.ResetPasswordRequest;
import com.WellSpace.modules.usuario.DTO.UsuarioLogin;
import com.WellSpace.modules.usuario.DTO.UsuarioRegristro;
import com.WellSpace.modules.usuario.exceptions.InvalidTokenException;
import com.WellSpace.modules.usuario.exceptions.TokenExpiredException;
import com.WellSpace.modules.usuario.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @Operation(summary = "Registrar usuário", description = "Registra um novo usuário e retorna um token de autenticação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping(value = "/registrar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> registrar(@ModelAttribute @Valid UsuarioRegristro usuarioRegristro) {
        String response = authService.registrarUsuario(usuarioRegristro);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UsuarioLogin usuarioLogin) {
        String token = authService.login(usuarioLogin);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> handleForgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
        try {
            authService.initiatePasswordResetProcess(forgotPasswordRequest.email());
            return ResponseEntity.ok("Se o seu e-mail estiver cadastrado em nosso sistema, você receberá um link para redefinir sua senha.");
        } catch (Exception e) {
            String userEmailForLog = (forgotPasswordRequest != null && forgotPasswordRequest.email() != null)
                    ? forgotPasswordRequest.email() : "Email não disponível na requisição";
            logger.error("Erro inesperado durante a solicitação de redefinição de senha para o email [{}]: ", userEmailForLog, e);
            return ResponseEntity.ok("Se o seu e-mail estiver cadastrado em nosso sistema, você receberá um link para redefinir sua senha.");
        }
    }

    @PostMapping("/reset-password") // Endpoint ajustado de "/resetar-senha" para "/reset-password"
    public ResponseEntity<String> handleResetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        try {
            authService.finalizePasswordReset(resetPasswordRequest.token(), resetPasswordRequest.newPassword());
            return ResponseEntity.ok("Sua senha foi redefinida com sucesso.");
        } catch (InvalidTokenException | TokenExpiredException e) {
            logger.warn("Tentativa de redefinição de senha falhou: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Para o token, é bom não logar o valor completo diretamente por segurança.
            // Pode-se logar um hash, ou apenas uma indicação de que um token foi usado.
            logger.error("Erro inesperado ao tentar redefinir a senha com o token [PROTEGIDO/PRESENTE]: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro ao tentar redefinir sua senha. Por favor, tente novamente mais tarde.");
        }
    }
}