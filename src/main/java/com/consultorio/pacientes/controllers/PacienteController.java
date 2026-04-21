package com.consultorio.pacientes.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.pacientes.dtos.PacienteDTO;
import com.consultorio.pacientes.dtos.PacienteResponseDTO;
import com.consultorio.pacientes.dtos.HistoriaClinicaDTO;
import com.consultorio.pacientes.dtos.HistoriaClinicaResponseDTO;
import com.consultorio.pacientes.exception.ResourceNotFoundException;
import com.consultorio.pacientes.services.HistoriaClinicaService;
import com.consultorio.pacientes.services.PacienteService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService service;
    private final HistoriaClinicaService historiaClinicaService;

    public PacienteController(PacienteService service, HistoriaClinicaService historiaClinicaService) {
        this.service = service;
        this.historiaClinicaService = historiaClinicaService;
    }

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> crearPaciente(
        @Valid @RequestBody PacienteDTO dto) {

    PacienteResponseDTO guardado = service.crear(dto);

    return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
}

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizarPaciente(
                @PathVariable Long id,
                @RequestBody PacienteDTO paciente) {

    return ResponseEntity.ok(service.actualizar(id, paciente));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> actualizarPacienteParcial(
                @PathVariable Long id,
                @RequestBody PacienteDTO paciente) {

    return ResponseEntity.ok(service.actualizarParcial(id, paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }


    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {
        List<PacienteResponseDTO> pacientes = service.listarTodos();
        return ResponseEntity.ok(pacientes); // 200 OK + lista
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> obtenerPaciente(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // Alias para frontend: /api/pacientes/{id}/historia-clinica
    @GetMapping("/{id}/historia-clinica")
    public ResponseEntity<HistoriaClinicaResponseDTO> obtenerHistoriaClinicaPorPaciente(@PathVariable Long id) {
        HistoriaClinicaResponseDTO historia = historiaClinicaService
                .obtenerHistoriaPorPaciente(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historia clínica no encontrada"));

        return ResponseEntity.ok(historia);
    }

    // Alias para frontend: /api/pacientes/{id}/historia-clinica
    @PostMapping("/{id}/historia-clinica")
    public ResponseEntity<HistoriaClinicaResponseDTO> crearHistoriaClinicaPorPaciente(
            @PathVariable Long id,
            @RequestBody HistoriaClinicaDTO dto) {
        HistoriaClinicaResponseDTO historia = historiaClinicaService.crearHistoriaClinica(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(historia);
    }

    // Alias para frontend: /api/pacientes/{id}/historia-clinica
    @PutMapping("/{id}/historia-clinica")
    public ResponseEntity<HistoriaClinicaResponseDTO> actualizarHistoriaClinicaPorPaciente(
            @PathVariable Long id,
            @RequestBody HistoriaClinicaDTO dto) {
        HistoriaClinicaResponseDTO historiaExistente = historiaClinicaService
                .obtenerHistoriaPorPaciente(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historia clínica no encontrada"));
        HistoriaClinicaResponseDTO historia = historiaClinicaService.actualizarHistoriaClinica(historiaExistente.getId(), dto);
        return ResponseEntity.ok(historia);
    }

}
