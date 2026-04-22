package com.consultorio.pacientes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.pacientes.dtos.TurnoDTO;
import com.consultorio.pacientes.dtos.TurnoResponseDTO;

import com.consultorio.pacientes.services.TurnoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

     private final TurnoService turnoService;

     public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TurnoResponseDTO> crear(@Valid @RequestBody TurnoDTO dto) {
        TurnoResponseDTO guardado = turnoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
