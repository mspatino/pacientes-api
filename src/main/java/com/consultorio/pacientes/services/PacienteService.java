package com.consultorio.pacientes.services;

import java.util.List;
import java.util.Optional;

import com.consultorio.pacientes.dtos.PacienteDTO;
import com.consultorio.pacientes.dtos.PacienteResponseDTO;


public interface PacienteService {

    // public Paciente guardar(Paciente paciente);

    public PacienteResponseDTO crear(PacienteDTO dto);

    public PacienteResponseDTO actualizar(Long id, PacienteDTO datos);

    public boolean eliminar(Long id);

    public List<PacienteResponseDTO> listarTodos();

    public PacienteResponseDTO obtenerPorId(Long id);

}
