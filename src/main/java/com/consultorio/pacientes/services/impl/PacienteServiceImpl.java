package com.consultorio.pacientes.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.consultorio.pacientes.entities.Paciente;
import com.consultorio.pacientes.repositories.PacienteRepository;
import com.consultorio.pacientes.services.PacienteService;

@Service
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository repository;

    public PacienteServiceImpl(PacienteRepository repository) {
        this.repository = repository;
    }


    public Paciente guardar(Paciente paciente) {
        paciente.setFechaAlta(LocalDateTime.now());
        return repository.save(paciente);

    }


    public Optional<Paciente> actualizar(Long id, Paciente datos) {
    return repository.findById(id).map(paciente -> {

        if (datos.getNombre() != null) {
            paciente.setNombre(datos.getNombre());
        }
        if (datos.getApellido() != null) {
            paciente.setApellido(datos.getApellido());
        }
        if (datos.getDni() != null) {
            paciente.setDni(datos.getDni());
        }
        if (datos.getDireccion() != null) {
            paciente.setDireccion(datos.getDireccion());
        }
        if (datos.getTelefono() != null) {
            paciente.setTelefono(datos.getTelefono());
        }
        if (datos.getOcupacion() != null) {
            paciente.setOcupacion(datos.getOcupacion());
        }
        if (datos.getFechaNacimiento() != null) {
            paciente.setFechaNacimiento(datos.getFechaNacimiento());
        }

        // NO tocamos fechaAlta
        return repository.save(paciente);
    });
}

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Paciente> listarTodos() {
        return repository.findAll();
    }

    public Optional<Paciente> obtenerPorId(Long id) {
        return repository.findById(id);
    }

}
