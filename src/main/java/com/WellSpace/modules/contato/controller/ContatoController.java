package com.WellSpace.modules.contato.controller;

import com.WellSpace.modules.contato.DTO.ContatoRequest;
import com.WellSpace.modules.contato.DTO.ContatoResponse;
import com.WellSpace.modules.contato.services.interfaces.ContatoServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/contato")
@AllArgsConstructor
public class ContatoController {

    private final ContatoServiceInterface contatoServiceInterface;

    @Operation(summary = "Criar contato", description = "Cria um novo contato.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping("/criar-contato")
    public ResponseEntity<ContatoResponse> criarContato(@RequestBody @Valid ContatoRequest contatoRequest) {
        try {
            ContatoResponse contatoResponse = contatoServiceInterface.criarContato(contatoRequest);
            return ResponseEntity.status(201).body(contatoResponse); // Retorna o contato criado com status 201 (Created)
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null); // Em caso de erro, retorna 400 e não retorna conteúdo
        }
    }
}
