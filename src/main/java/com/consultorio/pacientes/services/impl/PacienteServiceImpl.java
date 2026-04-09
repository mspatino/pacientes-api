package com.consultorio.pacientes.services.impl;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;

import com.consultorio.pacientes.dtos.PacienteDTO;
import com.consultorio.pacientes.dtos.PacienteResponseDTO;
import com.consultorio.pacientes.entities.Paciente;
import com.consultorio.pacientes.exception.ResourceNotFoundException;
import com.consultorio.pacientes.mapper.PacienteMapper;
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

    public PacienteResponseDTO crear(PacienteDTO dto) {
        Paciente saved = repository.save(PacienteMapper.toEntity(dto));
        return PacienteMapper.toDTO(saved);
    }


    public PacienteResponseDTO actualizar(Long id, PacienteDTO datos) {
        return actualizarParcial(id, datos);
}

    public PacienteResponseDTO actualizarParcial(Long id, PacienteDTO datos) {
      
    Paciente paciente = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

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
        if (datos.getNivelEducativo() != null) {
            paciente.setNivelEducativo(datos.getNivelEducativo());
        }
        if (datos.getEstadoCivil() != null) {
            paciente.setEstadoCivil(datos.getEstadoCivil());
        }
        if (datos.getSexo() != null) {
            paciente.setSexo(datos.getSexo());
        }
        if (datos.getEmail() != null) {
            paciente.setEmail(datos.getEmail());
        }
        if (datos.getConvivientes() != null) {
            paciente.setConvivientes(datos.getConvivientes());
        }
        if (datos.getFechaNacimiento() != null) {
            paciente.setFechaNacimiento(datos.getFechaNacimiento());
        }

        Paciente saved = repository.save(paciente);

        // NO tocamos fechaAlta
        return PacienteMapper.toDTO(saved)  ;
}

    public boolean eliminar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // public List<Paciente> listarTodos() {
    //     return repository.findAll();
    // }
public List<PacienteResponseDTO> listarTodos() {
    //   return repository.findAll()
    //         .stream()
    //         .map(PacienteMapper::toDTO)
    //         .toList();
    return PacienteMapper.toDTOList(repository.findAll());
    }


    public PacienteResponseDTO obtenerPorId(Long id) {
    Paciente paciente = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

    return PacienteMapper.toDTO(paciente);
}

}
