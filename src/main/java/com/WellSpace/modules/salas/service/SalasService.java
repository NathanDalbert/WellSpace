package com.WellSpace.modules.salas.service;

import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;
import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.salas.repository.SalasRepository;
import com.WellSpace.modules.salas.service.Mapper.SalasMapper;
import com.WellSpace.modules.salas.service.interfaces.SalasServiceInterface;
import com.WellSpace.modules.salas.exceptions.SalaHJaExisteException;
import com.WellSpace.modules.salas.exceptions.SalaNaoEncontradaException;
import com.WellSpace.modules.salas.exceptions.TempoInvalidoException;
import com.WellSpace.modules.usuario.domain.ENUM.UsuarioRole;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalasService implements SalasServiceInterface {

    private final SalasRepository salasRepository;
    private final SalasMapper salasMapper;
    private final UsuarioRepository usuarioRepository;

    @Override
    public SalasResponse criarSala(SalasRequest salasRequest, UUID usuarioId) {

        if (salasRepository.existsByNomeSala(salasRequest.nomeSala())) {
            throw new SalaHJaExisteException("Já existe uma sala com o nome " + salasRequest.nomeSala());
        }

        if (salasRequest.disponibilidadeInicio().isAfter(salasRequest.disponibilidadeFim())) {
            throw new TempoInvalidoException("O horário de início não pode ser depois do horário de fim.");
        }


        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        Salas sala = salasMapper.toEntity(salasRequest);


        sala.setUsuario(usuario);


        Salas salaSalva = salasRepository.save(sala);


        usuario.setUsuarioRole(UsuarioRole.LOCATARIO);
        usuarioRepository.save(usuario);


        return salasMapper.toResponse(salaSalva);
    }

    @Override
    public List<SalasResponse> listarSalas() {
        List<Salas> salas = salasRepository.findAll();
        return salas.stream()
                .map(salasMapper::toResponse)
                .toList();
    }

    @Override
    public List<SalasResponse> listarSalasPorDisponibilidade(DisponibilidadeSalaEnum disponibilidadeSala) {
        List<Salas> salas = salasRepository.findByDisponibilidadeSala(disponibilidadeSala);
        return salas.stream()
                .map(salasMapper::toResponse)
                .toList();
    }

    @Override
    public List<SalasResponse> buscarSalasPorHorario(LocalTime inicio, LocalTime fim) {
        List<Salas> salas = salasRepository.findAll();
        return salas.stream()
                .filter(sala -> isWithinTimeRange(sala, inicio, fim))
                .map(salasMapper::toResponse)
                .toList();
    }

    @Override
    public void deletarSala(UUID id) {

        if (!salasRepository.existsById(id)) {
            throw new SalaNaoEncontradaException("Sala com ID " + id + " não encontrada para exclusão.");
        }
        salasRepository.deleteById(id);
    }

    @Override
    public SalasResponse alterarDisponibilidade(UUID id, DisponibilidadeSalaEnum disponibilidadeSala) {
        Optional<Salas> salaOptional = salasRepository.findById(id);
        if (salaOptional.isEmpty()) {
            throw new SalaNaoEncontradaException("Sala com ID " + id + " não encontrada para alteração de disponibilidade.");
        }

        Salas sala = salaOptional.get();
        sala.setDisponibilidadeSala(disponibilidadeSala);
        Salas salaAtualizada = salasRepository.save(sala);

        return salasMapper.toResponse(salaAtualizada);
    }

    private boolean isWithinTimeRange(Salas sala, LocalTime inicio, LocalTime fim) {
        return !sala.getDisponibilidadeInicio().isBefore(inicio) && !sala.getDisponibilidadeFim().isAfter(fim);
    }

    // Novo método para buscar salas por usuarioId
    public List<SalasResponse> listarSalasPorUsuario(UUID usuarioId) {
        Optional<Salas> salas = salasRepository.findById(usuarioId);
        return salas.stream()
                .map(salasMapper::toResponse)
                .toList();
    }
}
