package com.WellSpace.modules.contato.services;

import com.WellSpace.modules.contato.domain.Contato;
import com.WellSpace.modules.contato.DTO.ContatoRequest;
import com.WellSpace.modules.contato.DTO.ContatoResponse;
import com.WellSpace.modules.contato.services.mappers.ContatoMapper;
import com.WellSpace.modules.contato.repository.ContatoRepository;
import com.WellSpace.modules.contato.services.interfaces.ContatoServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService implements ContatoServiceInterface {

    private final ContatoRepository contatoRepository;

    private final ContatoMapper contatoMapper;

    @Override
    public ContatoResponse criarContato(ContatoRequest contatoRequest) {
        Contato contato = contatoMapper.toEntity(contatoRequest);
        Contato contatoSalvo = contatoRepository.save(contato);
        return contatoMapper.toResponse(contatoSalvo);
    }

    @Override
    public ContatoResponse atualizarContato(UUID contatoId, ContatoRequest contatoRequest) {
        Contato contatoExistente = contatoRepository.findById(contatoId)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado"));
        contatoMapper.updateEntityFromRequest(contatoRequest, contatoExistente);
        Contato contatoAtualizado = contatoRepository.save(contatoExistente);
        return contatoMapper.toResponse(contatoAtualizado);
    }

    @Override
    public ContatoResponse buscarContatoPorId(UUID contatoId) {
        Contato contato = contatoRepository.findById(contatoId)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado"));
        return contatoMapper.toResponse(contato);
    }

    @Override
    public List<ContatoResponse> buscarContatosPorUsuario(UUID usuarioId) {
        List<Contato> contatos = contatoRepository.findByUsuario_UsuarioId(usuarioId);
        return contatos.stream()
                .map(contatoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void excluirContato(UUID contatoId) {
        Contato contato = contatoRepository.findById(contatoId)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado"));
        contatoRepository.delete(contato);
    }
}
