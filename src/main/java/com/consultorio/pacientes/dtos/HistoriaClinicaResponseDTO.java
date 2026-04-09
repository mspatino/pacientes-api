package com.consultorio.pacientes.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoriaClinicaResponseDTO {

    private Long id;
    private Long pacienteId;
    private LocalDateTime fechaAlta;
    private String motivoConsulta;
    private String observaciones;
    private String medicacion;
    private String consumo;
    private String tratamientosAnteriores;
    private boolean activa;
    private List<DiagnosticoResponseDTO> diagnosticos;

    // public HistoriaClinicaResponseDTO() {}

    // public HistoriaClinicaResponseDTO(Long id, Long pacienteId, LocalDateTime fechaAlta,
    //         String motivoConsulta, String observaciones, Boolean activa) {
    //     this.id = id;
    //     this.pacienteId = pacienteId;
    //     this.fechaAlta = fechaAlta;
    //     this.motivoConsulta = motivoConsulta;
    //     this.observaciones = observaciones;
    //     this.activa = activa;
    // }

}
