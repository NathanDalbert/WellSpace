package com.WellSpace.modules.reservas.services;

import com.WellSpace.modules.reservas.DTO.ReservaRequest;
import com.WellSpace.modules.reservas.DTO.ReservaResponse;
import com.WellSpace.modules.reservas.domain.Reserva;
import com.WellSpace.modules.reservas.repository.ReservaRepository;
import com.WellSpace.modules.reservas.services.interfaces.ReservaServiceInterface;
import com.WellSpace.modules.reservas.services.mapper.ReservaMapper;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.salas.repository.SalasRepository;
import com.WellSpace.modules.usuario.domain.Usuario;
import com.WellSpace.modules.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // IMPORTANTE
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService implements ReservaServiceInterface {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final UsuarioRepository usuarioRepository;
    private final SalasRepository salasRepository;

    @Override
    @Transactional
    public ReservaResponse criarReserva(ReservaRequest request) {
        Salas salas = salasRepository.findById(request.salas())
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));
        Usuario locatario = usuarioRepository.findById(request.locatario())
                .orElseThrow(() -> new EntityNotFoundException("Locatário não encontrado"));
        Usuario locador = usuarioRepository.findById(request.locador())
                .orElseThrow(() -> new EntityNotFoundException("Locador não encontrado"));

        Reserva reserva = reservaMapper.toEntity(request, salas, locatario, locador);
        Reserva savedReserva = reservaRepository.save(reserva);
        return reservaMapper.toResponseDTO(savedReserva);
    }

    @Override
    @Transactional(readOnly = true) // ADICIONADO
    public ReservaResponse buscarPorId(UUID id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
        return reservaMapper.toResponseDTO(reserva);
    }

    @Override
    @Transactional(readOnly = true) // ADICIONADO
    public List<ReservaResponse> listarTodas() {
        return reservaRepository.findAll()
                .stream()
                .map(reservaMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public void deletarReserva(UUID id) {
        if (!reservaRepository.existsById(id)) {
            throw new EntityNotFoundException("Reserva não encontrada para exclusão");
        }
        reservaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponse> buscarPorLocatarioId(UUID locatarioId) {
        usuarioRepository.findById(locatarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário locatário não encontrado"));

        List<Reserva> reservas = reservaRepository.findByLocatarioUsuarioId(locatarioId);

        return reservas.stream()
                .map(reservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservaResponse> listarPorLocadorId(UUID locadorId) {
        List<Reserva> reservas = reservaRepository.findByLocadorUsuarioId(locadorId);

        return reservas.stream()
                .map(reservaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}