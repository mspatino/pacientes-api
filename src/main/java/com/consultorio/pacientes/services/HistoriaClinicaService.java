package com.consultorio.pacientes.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.consultorio.pacientes.dtos.HistoriaClinicaDTO;
import com.consultorio.pacientes.entities.HistoriaClinica;

public interface HistoriaClinicaService {

    public HistoriaClinica crearHistoria(Long pacienteId, HistoriaClinicaDTO dto) ;
   
    public HistoriaClinica actualizarHistoria(Long historiaId, HistoriaClinicaDTO dto) ;
   
    public HistoriaClinica cerrarHistoria(Long historiaId) ;

    public Optional<HistoriaClinica> obtenerHistoriaPorPaciente(Long pacienteId);

    public Optional<HistoriaClinica> obtenerPorId(Long id);

    //Esto permite hacer búsquedas:por DNI,por nombre,por apellido,por estado (activa),por rango de fechas, todo en una sola consulta

    public  Page<HistoriaClinica> buscarHistorias(
            String dni,
            String nombre,
            String apellido,
            Boolean activa,
            LocalDate desde,
            LocalDate hasta,
            Pageable pageable
    );

    //hacer filtros dinámicos usando Specification en Spring Data JPA dentro de Spring Boot con Java.
    //Esto es lo que usan muchos sistemas grandes porque permite agregar filtros sin reescribir queries.
    public  Page<HistoriaClinica> buscar(
            String dni,
            String nombre,
            String apellido,
            Boolean activa,
            LocalDate desde,
            LocalDate hasta,
            Pageable pageable
    );


}
