package com.WellSpace.modules.reservas.controller;

import com.WellSpace.modules.reservas.DTO.ReservaRequest;
import com.WellSpace.modules.reservas.DTO.ReservaResponse;

import com.WellSpace.modules.reservas.services.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponse> criarReserva(@Valid @RequestBody ReservaRequest request) {
        ReservaResponse response = reservaService.criarReserva(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> listarTodasReservas() {
        List<ReservaResponse> reservas = reservaService.listarTodas();
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> buscarReservaPorId(@PathVariable UUID id) {
        try {
            ReservaResponse reserva = reservaService.buscarPorId(id);
            return new ResponseEntity<>(reserva, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReserva(@PathVariable UUID id) {
        try {
            reservaService.deletarReserva(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
