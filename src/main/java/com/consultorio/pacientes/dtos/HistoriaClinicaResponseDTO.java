package com.consultorio.pacientes.dtos;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoriaClinicaResponseDTO {

    private Long id;
    private Long pacienteId;
    private LocalDateTime fechaAlta;
    private String motivoConsulta;
    private String observaciones;
    private Boolean activa;

    public HistoriaClinicaResponseDTO() {}

    public HistoriaClinicaResponseDTO(Long id, Long pacienteId, LocalDateTime fechaAlta,
            String motivoConsulta, String observaciones, Boolean activa) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.fechaAlta = fechaAlta;
        this.motivoConsulta = motivoConsulta;
        this.observaciones = observaciones;
        this.activa = activa;
    }

}
