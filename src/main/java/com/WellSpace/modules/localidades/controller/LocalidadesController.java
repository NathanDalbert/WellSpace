package com.WellSpace.modules.localidades.controller;

import com.WellSpace.modules.localidades.DTO.LocalidadesRequest;
import com.WellSpace.modules.localidades.DTO.LocalidadesResponse;
import com.WellSpace.modules.localidades.services.LocalidadesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/localidades")
@RequiredArgsConstructor
public class LocalidadesController {

    private final LocalidadesService localidadesService;



    @PostMapping
    public ResponseEntity<LocalidadesResponse> createLocalidade(
            @RequestBody LocalidadesRequest requestDTO,
            @RequestHeader("Usuario-Id") UUID usuarioid) {

        LocalidadesResponse localidadeResponse = localidadesService.createLocalidade(requestDTO, usuarioid);
        return new ResponseEntity<>(localidadeResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LocalidadesResponse>> getAllLocalidades() {
        List<LocalidadesResponse> localidadesResponse = localidadesService.getAllLocalidades();
        return new ResponseEntity<>(localidadesResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalidadesResponse> getLocalidadeById(@PathVariable UUID id) {
        Optional<LocalidadesResponse> localidadeResponse = localidadesService.getLocalidadeById(id);
        return localidadeResponse
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
