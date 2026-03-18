package com.consultorio.pacientes.services;

import java.util.List;

import com.consultorio.pacientes.dtos.DiagnosticoDTO;
import com.consultorio.pacientes.dtos.DiagnosticoResponseDTO;


public interface DiagnosticoService {

    public DiagnosticoResponseDTO crearDiagnostico(
            Long historiaClinicaId, DiagnosticoDTO diagnostico);
            
    public List<DiagnosticoResponseDTO> listarPorHistoria(Long historiaId);    
    public DiagnosticoResponseDTO obtenerPrincipal(Long historiaId);
    public DiagnosticoResponseDTO actualizarDiagnostico(
            Long diagnosticoId, DiagnosticoDTO diagnostico);

    public void eliminarDiagnostico(Long diagnosticoId);
}
