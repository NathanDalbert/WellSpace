package com.WellSpace.modules.salas.controller;

import com.WellSpace.modules.salas.DTO.SalasRequest;
import com.WellSpace.modules.salas.DTO.SalasResponse;
import com.WellSpace.modules.salas.service.interfaces.SalasServiceInterface;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salas")
@RequiredArgsConstructor
public class SalasController {
    private final SalasServiceInterface salasServiceInterface;

    @PostMapping("/criar-sala")
    public ResponseEntity<SalasResponse> criarSala(@RequestBody SalasRequest salasRequest) {
        try {
            SalasResponse response = salasServiceInterface.criarSala(salasRequest);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
