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

import com.consultorio.pacientes.entities.Paciente;
import com.consultorio.pacientes.services.PacienteService;


@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) {
        this.service = service;
    }

        // Crear un paciente
    // @PostMapping
    // public Paciente crearPaciente(@RequestBody Paciente paciente) {
    //     return service.guardar(paciente);
    // }

    @PostMapping
    public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
    Paciente guardado = service.guardar(paciente);
    return ResponseEntity.status(HttpStatus.CREATED).body(guardado); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(
                @PathVariable Long id,
                @RequestBody Paciente paciente) {

            return service.actualizar(id, paciente)
                    .map(p -> ResponseEntity.ok(p))          // 200 OK
                    .orElse(ResponseEntity.notFound().build()); // 404
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }


    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = service.listarTodos();
        return ResponseEntity.ok(pacientes); // 200 OK + lista
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPaciente(@PathVariable Long id) {
    return service.obtenerPorId(id)
            .map(p -> ResponseEntity.ok(p))         // 200 OK
            .orElse(ResponseEntity.notFound().build()); // 404 Not Found
}



}
