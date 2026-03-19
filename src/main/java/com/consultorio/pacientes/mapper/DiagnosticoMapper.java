package com.consultorio.pacientes.mapper;

import com.consultorio.pacientes.dtos.DiagnosticoResponseDTO;
import com.consultorio.pacientes.entities.Diagnostico;

public class DiagnosticoMapper {

    public static DiagnosticoResponseDTO toDTO(Diagnostico d) {

        DiagnosticoResponseDTO dto = new DiagnosticoResponseDTO();

        dto.setId(d.getId());
        dto.setDescripcion(d.getDescripcion());
        dto.setEvolucion(d.getEvolucion());
        dto.setTratamiento(d.getTratamiento());
    
        dto.setPrincipal(d.isPrincipal());
        dto.setFecha(d.getFecha());

        // historia clínica
        if (d.getHistoriaClinica() != null) {
            dto.setHistoriaClinicaId(d.getHistoriaClinica().getId());
        }

        // CIE10 (opcional)
        if (d.getCie10() != null) {
            dto.setCie10Codigo(d.getCie10().getCodigo());
            dto.setCie10Descripcion(d.getCie10().getDescripcion());
        }

        return dto;
    }
}