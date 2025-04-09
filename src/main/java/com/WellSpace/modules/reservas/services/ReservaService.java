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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservaService implements ReservaServiceInterface {

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final UsuarioRepository usuarioRepository;
    private final SalasRepository salasRepository;

    @Transactional
    @Override
    public ReservaResponse criarReserva(ReservaRequest request) {

        Salas salas = salasRepository.findById(request.salas())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        Usuario locatario = usuarioRepository.findById(request.locatario())
                .orElseThrow(() -> new RuntimeException("Locatário não encontrado"));

        Usuario locador = usuarioRepository.findById(request.locador())
                .orElseThrow(() -> new RuntimeException("Locador não encontrado"));


        Reserva reserva = reservaMapper.toEntity(request, salas, locatario, locador);
        Reserva savedReserva = reservaRepository.save(reserva);

        return reservaMapper.toResponseDTO(savedReserva);
    }

    @Override
    public ReservaResponse buscarPorId(UUID id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        return reservaMapper.toResponseDTO(reserva);
    }

    @Override
    public List<ReservaResponse> listarTodas() {
        return reservaRepository.findAll()
                .stream()
                .map(reservaMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    @Override
    public void deletarReserva(UUID id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva não encontrada para exclusão");
        }
        reservaRepository.deleteById(id);
    }
}
