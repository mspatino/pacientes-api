package com.consultorio.pacientes.mapper;

import org.springframework.stereotype.Component;

import com.consultorio.pacientes.dtos.Cie10DTO;
import com.consultorio.pacientes.dtos.DiagnosticoResponseDTO;
import com.consultorio.pacientes.entities.Diagnostico;

@Component
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
        // if (d.getCie10() != null) {
        //     dto.setCie10Codigo(d.getCie10().getCodigo());
        //     dto.setCie10Descripcion(d.getCie10().getDescripcion());
        // }
        if (d.getCie10() != null) {
            Cie10DTO cie10DTO = new Cie10DTO();
            cie10DTO.setCodigo(d.getCie10().getCodigo());
            cie10DTO.setDescripcion(d.getCie10().getDescripcion());
            dto.setCie10(cie10DTO);
        }

        return dto;
    }
}