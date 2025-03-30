package com.WellSpace.modules.contato.services.interfaces;

import com.WellSpace.modules.contato.DTO.ContatoRequest;
import com.WellSpace.modules.contato.DTO.ContatoResponse;

import java.util.List;
import java.util.UUID;

public interface ContatoServiceInterface {

    ContatoResponse criarContato(ContatoRequest contatoRequest);

    ContatoResponse atualizarContato(UUID contatoId, ContatoRequest contatoRequest);

    ContatoResponse buscarContatoPorId(UUID contatoId);

    List<ContatoResponse> buscarContatosPorUsuario(UUID usuarioId);

    void excluirContato(UUID contatoId);
}