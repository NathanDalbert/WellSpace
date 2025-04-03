package com.WellSpace.modules.salas.service;

import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;
import com.WellSpace.modules.salas.domain.ENUM.DisponibilidadeSalaEnum;
import com.WellSpace.modules.salas.domain.Salas;
import com.WellSpace.modules.salas.repository.SalasRepository;
import com.WellSpace.modules.salas.service.Mapper.SalasMapper;
import com.WellSpace.modules.salas.service.interfaces.SalasServiceInterface;
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


    @Override
    public SalasResponse criarSala(SalasRequest salasRequest) {

        Salas sala = salasMapper.toSalas(salasRequest);
        Salas salaSalva = salasRepository.save(sala);
        return salasMapper.toSalasResponse(salaSalva);
    }


    @Override
    public List<SalasResponse> listarSalas() {
        List<Salas> salas = salasRepository.findAll();
        return salas.stream()
                .map(salasMapper::toSalasResponse)
                .toList();
    }

    @Override
    public List<SalasResponse> listarSalasPorDisponibilidade(DisponibilidadeSalaEnum disponibilidadeSala) {
        List<Salas> salas = salasRepository.findByDisponibilidadeSala(disponibilidadeSala);
        return salas.stream()
                .map(salasMapper::toSalasResponse)
                .toList();
    }

    @Override
    public List<SalasResponse> buscarSalasPorHorario(LocalTime inicio, LocalTime fim) {
        List<Salas> salas = salasRepository.findAll();
        return salas.stream()
                .filter(sala -> sala.getDisponibilidadeInicio().isAfter(inicio) && sala.getDisponibilidadeFim().isBefore(fim))
                .map(salasMapper::toSalasResponse)
                .toList();
    }

    @Override
    public void deletarSala(UUID id) {
        if (salasRepository.existsById(id)) {
            salasRepository.deleteById(id);
        } else {
            throw new RuntimeException("Sala não encontrada para exclusão");
        }
    }

    @Override
    public SalasResponse alterarDisponibilidade(UUID id, DisponibilidadeSalaEnum disponibilidadeSala) {
        Optional<Salas> salaOptional = salasRepository.findById(id);
        if (salaOptional.isPresent()) {
            Salas sala = salaOptional.get();
            sala.setDisponibilidadeSala(disponibilidadeSala);
            Salas salaAtualizada = salasRepository.save(sala);
            return salasMapper.toSalasResponse(salaAtualizada);
        } else {
            throw new RuntimeException("Sala não encontrada para alteração de disponibilidade");
        }
    }
}
