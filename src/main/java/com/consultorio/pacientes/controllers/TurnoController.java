package com.consultorio.pacientes.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PutMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> actualizarTurno(
            @PathVariable Long id,
            @RequestBody TurnoDTO turno) {

        return ResponseEntity.ok(turnoService.actualizar(id, turno));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDTO> obtenerPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(turnoService.obtenerPorId(id));
    }

    @GetMapping("/dia")
    public ResponseEntity<List<TurnoResponseDTO>> getTurnosDelDia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.getTurnosDelDia(fecha));
    }

    @GetMapping("/semana")
    public ResponseEntity<List<TurnoResponseDTO>> getTurnosDeLaSemana(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(turnoService.getTurnosDeLaSemana(fecha));
    }

    @GetMapping("/mes")
    public ResponseEntity<List<TurnoResponseDTO>> getTurnosDelMes(
            @RequestParam int anio,
            @RequestParam int mes) {
        return ResponseEntity.ok(turnoService.getTurnosDelMes(anio, mes));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        turnoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

}
