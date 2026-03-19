package com.consultorio.pacientes.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.pacientes.dtos.DiagnosticoDTO;
import com.consultorio.pacientes.dtos.DiagnosticoResponseDTO;
import com.consultorio.pacientes.dtos.DiagnosticoUpdateDTO;
import com.consultorio.pacientes.services.DiagnosticoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoController {

    private final DiagnosticoService diagnosticoService;

    public DiagnosticoController(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

      //Crear un nuevo diagnóstico
    @PostMapping("/historia/{historiaId}")
    public ResponseEntity<DiagnosticoResponseDTO> crearDiagnostico(
            @PathVariable Long historiaId,
            @Valid @RequestBody DiagnosticoDTO req) {
        
        DiagnosticoResponseDTO diagnostico = diagnosticoService.crearDiagnostico(
                historiaId,req);        

        return ResponseEntity.status(HttpStatus.CREATED).body(diagnostico);
                
}

    // Listar todos los diagnósticos de una historia clínica
    @GetMapping("/historia/{historiaId}")
    public ResponseEntity<List<DiagnosticoResponseDTO>> listarPorHistoria(
            @PathVariable Long historiaId) {

        List<DiagnosticoResponseDTO> lista = diagnosticoService.listarPorHistoria(historiaId);
        return ResponseEntity.ok(lista);
    }

    //Obtener el diagnóstico principal de una historia clínica
    @GetMapping("/historia/{historiaId}/principal")
    public ResponseEntity<DiagnosticoResponseDTO> obtenerPrincipal(
            @PathVariable Long historiaId) {

        DiagnosticoResponseDTO principal = diagnosticoService.obtenerPrincipal(historiaId);
        return ResponseEntity.ok(principal);
    }

    //Actualizar un diagnóstico existente
    @PutMapping("/{diagnosticoId}")
    public ResponseEntity<DiagnosticoResponseDTO> actualizarDiagnostico(
            @PathVariable Long diagnosticoId,
            @Valid @RequestBody DiagnosticoUpdateDTO dto) {

        DiagnosticoResponseDTO actualizado = diagnosticoService.actualizarDiagnostico(diagnosticoId, dto);
        return ResponseEntity.ok(actualizado);
    }

    //Eliminar un diagnóstico
    @DeleteMapping("/{diagnosticoId}")
    public ResponseEntity<Void> eliminarDiagnostico(@PathVariable Long diagnosticoId) {
        diagnosticoService.eliminarDiagnostico(diagnosticoId);
        return ResponseEntity.noContent().build();
    }

}
