package com.WellSpace.modules.localidades.services;

import com.WellSpace.modules.localidades.DTO.LocalidadesRequest;
import com.WellSpace.modules.localidades.DTO.LocalidadesResponse;
import com.WellSpace.modules.localidades.domain.Localidades;
import com.WellSpace.modules.localidades.repository.LocalidadesRepository;
import com.WellSpace.modules.localidades.services.interfaces.LocalidadesServiceInterface;
import com.WellSpace.modules.localidades.services.mapper.LocalidadesMapper;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocalidadesService implements LocalidadesServiceInterface {

    private final LocalidadesRepository localidadesRepository;
    private final LocalidadesMapper localidadesMapper;
    private final UsuarioRepository usuarioRepository;



    @Transactional
    @Override
    public LocalidadesResponse createLocalidade(LocalidadesRequest requestDTO, UUID usuarioid) {
        Usuario usuario = usuarioRepository.findById(usuarioid).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Localidades localidade = localidadesMapper.toEntity(requestDTO, usuario);


        Localidades savedLocalidade = localidadesRepository.save(localidade);


        return localidadesMapper.toResponseDTO(savedLocalidade);
    }
    @Override
    public List<LocalidadesResponse> getAllLocalidades() {
        List<Localidades> localidades = localidadesRepository.findAll();
        return localidades.stream()
                .map(localidadesMapper::toResponseDTO)
                .toList();
    }

    @Override
    public Optional<LocalidadesResponse> getLocalidadeById(UUID localidadeId) {
        Optional<Localidades> localidadeOpt = localidadesRepository.findById(localidadeId);
        return localidadeOpt.map(localidadesMapper::toResponseDTO);
    }
}
