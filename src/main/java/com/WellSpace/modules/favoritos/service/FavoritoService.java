package com.WellSpace.modules.favoritos.service;

import com.WellSpace.modules.favoritos.DTO.FavoritoRequest;
import com.WellSpace.modules.favoritos.DTO.FavoritoResponse;
import com.WellSpace.modules.favoritos.domain.Favorito;
import com.WellSpace.modules.favoritos.exceptions.FavoritoJaExisteException;
import com.WellSpace.modules.favoritos.exceptions.FavoritoNaoEncontradoException;
import com.WellSpace.modules.favoritos.service.Mapper.FavoritoMapper;
import com.WellSpace.modules.favoritos.repository.FavoritoRepository;
import com.WellSpace.modules.favoritos.service.interfaces.FavoritoServiceInterface;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.salas.repository.SalasRepository;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritoService implements FavoritoServiceInterface {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalasRepository salasRepository;

    @Override
    public FavoritoResponse criarFavorito(FavoritoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Salas sala = salasRepository.findById(request.salaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada."));

        boolean jaExiste = favoritoRepository.findByUsuarioUsuarioIdAndSalaSalasId(usuario.getUsuarioId(), sala.getSalasId()).isPresent();
        if (jaExiste) {
            throw new FavoritoJaExisteException("Esta sala já está nos favoritos do usuário.");
        }

        Favorito favorito = Favorito.novoFavorito(usuario, sala);
        favoritoRepository.save(favorito);

        return FavoritoMapper.toResponse(favorito);
    }

    @Override
    public List<FavoritoResponse> listarFavoritosPorUsuario(UUID usuarioId) {
        List<Favorito> favoritos = favoritoRepository.findByUsuarioUsuarioId(usuarioId);
        return favoritos.stream()
                .map(FavoritoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarFavorito(UUID favoritoId) {
        Favorito favorito = favoritoRepository.findById(favoritoId)
                .orElseThrow(() -> new FavoritoNaoEncontradoException("Favorito não encontrado para exclusão."));
        favoritoRepository.delete(favorito);
    }
}
