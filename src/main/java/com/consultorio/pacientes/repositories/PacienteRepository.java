package com.consultorio.pacientes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.pacientes.entities.Paciente;


@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long>{


    // Método opcional para buscar por apellido: busca pacientes por apellido parcial, sin importar mayúsculas/minúsculas
    List<Paciente> findByApellidoContainingIgnoreCase(String apellido);

}
