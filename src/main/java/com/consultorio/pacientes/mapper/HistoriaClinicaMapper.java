package com.consultorio.pacientes.mapper;

import com.consultorio.pacientes.dtos.HistoriaClinicaResponseDTO;
import com.consultorio.pacientes.entities.HistoriaClinica;

public class HistoriaClinicaMapper {

        public static HistoriaClinicaResponseDTO toDTO(HistoriaClinica h) {
        return new HistoriaClinicaResponseDTO(
                h.getId(),
                h.getPaciente().getId(),
                h.getFechaAlta(),
                h.getMotivoConsulta(),
                h.getObservaciones(),
                h.getActiva()
        );
    }

}
