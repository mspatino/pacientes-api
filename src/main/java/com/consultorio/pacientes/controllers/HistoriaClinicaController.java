package com.consultorio.pacientes.controllers;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.pacientes.dtos.HistoriaClinicaDTO;
import com.consultorio.pacientes.dtos.HistoriaClinicaResponseDTO;
import com.consultorio.pacientes.entities.HistoriaClinica;
import com.consultorio.pacientes.services.HistoriaClinicaService;
import com.consultorio.pacientes.services.impl.HistoriaClinicaServiceImpl;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/historias")
public class HistoriaClinicaController {

    private final HistoriaClinicaService serviceHistoria;

    public HistoriaClinicaController(HistoriaClinicaServiceImpl serviceHistoria) {
        this.serviceHistoria = serviceHistoria;
    }

        // Crear historia clínica
    // @PostMapping("/paciente/{pacienteId}")
    // public ResponseEntity<HistoriaClinica> crearHistoria(
    //         @PathVariable Long pacienteId,
    //         @RequestBody HistoriaClinicaDTO dto) {
    //     try {
    //         HistoriaClinica historia = serviceHistoria.crearHistoria(pacienteId, dto);
    //         return ResponseEntity.status(HttpStatus.CREATED).body(historia);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                              .body(null);
    //     }
    // }

    @PostMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinicaResponseDTO> crearHistoria(
        @PathVariable Long pacienteId,
        @RequestBody HistoriaClinicaDTO dto) {

    HistoriaClinicaResponseDTO historia = serviceHistoria.crearHistoriaClinica(pacienteId, dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(historia);
    }


    @PutMapping("/{historiaId}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long historiaId,
            @RequestBody HistoriaClinicaDTO dto) {
        try {
            HistoriaClinicaResponseDTO historiaActualizada = serviceHistoria.actualizarHistoriaClinica(historiaId, dto);
            return ResponseEntity.status(HttpStatus.OK).body(historiaActualizada);
        }
         catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error inesperado al actualizar la historia clínica");
    }
       
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<?> cerrarHistoria(@PathVariable Long id) {

    try {
        HistoriaClinicaResponseDTO historia = serviceHistoria.cerrarHistoriaClinica(id);
        
        return ResponseEntity.ok(historia);

    } catch (EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

    } catch (IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}

    // Obtener historia clínica de un paciente
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistoriaClinicaResponseDTO> obtenerHistoriaPorPaciente(@PathVariable Long pacienteId) {
        //HistoriaClinicaResponseDTO historia = serviceHistoria.obtenerHistoriaPorPaciente(pacienteId);
        
          HistoriaClinicaResponseDTO historia = serviceHistoria
            .obtenerHistoriaPorPaciente(pacienteId)
            .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));

          return ResponseEntity.ok(historia);

    }

    // Obtener historia clínica por ID
    // @GetMapping("/{id}")
    // public ResponseEntity<HistoriaClinicaResponseDTO> obtenerPorId(@PathVariable Long id) {
    
    //     return serviceHistoria.obtenerPorId(id)
    //         .map( p -> ResponseEntity.ok(p)) //ok
    //         .orElse(ResponseEntity.notFound().build()); //404 not found
    // }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriaClinicaResponseDTO> obtenerPorId(@PathVariable Long id) {

        HistoriaClinicaResponseDTO dto = serviceHistoria.obtenerPorId(id);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/buscar")
    public Page<HistoriaClinica> buscarHistorias(
            @RequestParam(required = false) String dni,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) Boolean activa,
            @RequestParam(required = false) LocalDate desde,
            @RequestParam(required = false) LocalDate hasta,
            Pageable pageable
    ) {

        return serviceHistoria.buscarHistorias(dni,nombre,apellido,activa,desde,hasta,pageable);
    }

        

}
