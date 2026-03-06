package com.consultorio.pacientes.services;

import java.util.List;
import java.util.Optional;

import com.consultorio.pacientes.entities.Paciente;

public interface PacienteService {

    public Paciente guardar(Paciente paciente);

    public Optional<Paciente> actualizar(Long id, Paciente datos);

    public boolean eliminar(Long id);

    public List<Paciente> listarTodos();

    public Optional<Paciente> obtenerPorId(Long id);

}
