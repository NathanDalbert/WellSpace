package com.WellSpace.modules.contato.services;

import com.WellSpace.modules.contato.domain.Contato;
import com.WellSpace.modules.contato.DTO.ContatoRequest;
import com.WellSpace.modules.contato.DTO.ContatoResponse;
import com.WellSpace.modules.contato.exceptions.ContatoNãoEncontradoException;
import com.WellSpace.modules.contato.exceptions.ContatoJaExisteException;
import com.WellSpace.modules.contato.exceptions.ContatoNãoEncontradoException;
import com.WellSpace.modules.contato.exceptions.DadosDeContatoInvalidosException;
import com.WellSpace.modules.contato.exceptions.ErroGenericoException;
import com.WellSpace.modules.contato.repository.ContatoRepository;
import com.WellSpace.modules.contato.services.interfaces.ContatoServiceInterface;
import com.WellSpace.modules.contato.services.mappers.ContatoMapper;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
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
    private final UsuarioRepository usuarioRepository;

    @Override
    public ContatoResponse criarContato(ContatoRequest contatoRequest) {

        if (contatoRepository.existsByUsuario_UsuarioIdAndContato(contatoRequest.usuario_id(),
                contatoRequest.contato())) {
            throw new ContatoJaExisteException(contatoRequest.contato());
        }

        if (contatoRequest.contato() == null || contatoRequest.contato().isEmpty()) {
            throw new DadosDeContatoInvalidosException("Contato não pode ser nulo ou vazio.");
        }

        Usuario usuario = usuarioRepository.findById(contatoRequest.usuario_id())
                .orElseThrow(() -> new ContatoNãoEncontradoException(contatoRequest.usuario_id()));

        try {
            Contato contato = new Contato(usuario, contatoRequest.contato());
            Contato contatoSalvo = contatoRepository.save(contato);
            return contatoMapper.toResponse(contatoSalvo);
        } catch (Exception e) {
            throw new ErroGenericoException("Erro ao criar o contato: " + e.getMessage());
        }
    }

    @Override
    public ContatoResponse atualizarContato(UUID contatoId, ContatoRequest contatoRequest) {
        Contato contatoExistente = contatoRepository.findById(contatoId)
                .orElseThrow(() -> new ContatoNãoEncontradoException(contatoId));

        if (contatoRequest.contato() == null || contatoRequest.contato().isEmpty()) {
            throw new DadosDeContatoInvalidosException("Contato não pode ser nulo ou vazio.");
        }

        try {
            contatoExistente.setContato(contatoRequest.contato());
            Contato contatoAtualizado = contatoRepository.save(contatoExistente);
            return contatoMapper.toResponse(contatoAtualizado);
        } catch (Exception e) {
            throw new ErroGenericoException("Erro ao atualizar o contato: " + e.getMessage());
        }
    }

    @Override
    public ContatoResponse buscarContatoPorId(UUID contatoId) {
        Contato contato = contatoRepository.findById(contatoId)
                .orElseThrow(() -> new ContatoNãoEncontradoException(contatoId));
        return contatoMapper.toResponse(contato);
    }

    @Override
    public List<ContatoResponse> buscarContatosPorUsuario(UUID usuario_id) {
        List<Contato> contatos = contatoRepository.findByUsuario_UsuarioId(usuario_id);
        return contatos.stream()
                .map(contatoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void excluirContato(UUID contatoId) {
        Contato contato = contatoRepository.findById(contatoId)
                .orElseThrow(() -> new ContatoNãoEncontradoException(contatoId));
        contatoRepository.delete(contato);
    }
}
