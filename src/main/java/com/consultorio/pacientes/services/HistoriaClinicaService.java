package com.consultorio.pacientes.services;

import java.util.Optional;

import com.consultorio.pacientes.dtos.HistoriaClinicaDTO;
import com.consultorio.pacientes.entities.HistoriaClinica;

public interface HistoriaClinicaService {

    public HistoriaClinica crearHistoria(Long pacienteId, HistoriaClinicaDTO dto) ;
   
    public HistoriaClinica actualizarHistoria(Long historiaId, HistoriaClinicaDTO dto) ;
   
    public HistoriaClinica cerrarHistoria(Long historiaId) ;

    public Optional<HistoriaClinica> obtenerHistoriaPorPaciente(Long pacienteId);

    public Optional<HistoriaClinica> obtenerPorId(Long id);

}
