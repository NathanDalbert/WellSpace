package com.WellSpace.modules.contato.controller;

import com.WellSpace.modules.contato.DTO.ContatoRequest;
import com.WellSpace.modules.contato.DTO.ContatoResponse;
import com.WellSpace.modules.contato.services.interfaces.ContatoServiceInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contato")
@RequiredArgsConstructor
public class ContatoController {
    @Operation(summary = "Criar contato", description = "Cria um novo contato.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contato criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados")
    })
    @PostMapping("/criar-contato")
    public ResponseEntity<ContatoResponse> criarContato(@RequestBody @Valid ContatoRequest contatoRequest) {
        try {
            ContatoResponse contatoResponse = contatoServiceInterface.criarContato(contatoRequest);
            return ResponseEntity.status(201).body(contatoResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private final ContatoServiceInterface contatoServiceInterface;

    @Operation(summary = "Buscar contatos por usuário", description = "Recupera todos os contatos de um usuário especificado pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contatos encontrados e retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado ou sem contatos")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ContatoResponse>> buscarContatosPorUsuario(@PathVariable UUID usuarioId) {
        try {
            List<ContatoResponse> contatos = contatoServiceInterface.buscarContatosPorUsuario(usuarioId);
            if (contatos.isEmpty()) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.ok(contatos);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @Operation(summary = "Buscar contato por ID", description = "Retorna o contato com o ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @GetMapping("/buscar-contato/{id}")
    public ResponseEntity<ContatoResponse> buscarContatoPorId(@PathVariable UUID id) {
        try {
            ContatoResponse contatoResponse = contatoServiceInterface.buscarContatoPorId(id);
            return ResponseEntity.ok(contatoResponse);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @Operation(summary = "Deletar contato", description = "Deleta o contato com o ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado para exclusão")
    })
    @DeleteMapping("/deletar-contato/{id}")
    public ResponseEntity<String> deletarContato(@PathVariable UUID id) {
        try {
            contatoServiceInterface.excluirContato(id);
            return ResponseEntity.status(200).body("Contato deletado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Contato não encontrado.");
        }
    }

    @Operation(summary = "Atualizar contato", description = "Atualiza o contato com o ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados de contato"),
            @ApiResponse(responseCode = "404", description = "Contato não encontrado")
    })
    @PutMapping("/atualizar-contato/{id}")
    public ResponseEntity<ContatoResponse> atualizarContato(@PathVariable UUID id,
            @RequestBody @Valid ContatoRequest contatoRequest) {
        try {
            ContatoResponse contatoResponse = contatoServiceInterface.atualizarContato(id, contatoRequest);
            return ResponseEntity.ok(contatoResponse);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}
