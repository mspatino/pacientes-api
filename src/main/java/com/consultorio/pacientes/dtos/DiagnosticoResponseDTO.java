package com.consultorio.pacientes.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoResponseDTO {

    private Long id;

    private String descripcion;
    private String evolucion;
    private String tratamiento;

    private Boolean principal;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    // CIE10 (opcional)
    private Cie10DTO cie10;

    // opcional (a veces útil para debug o frontend)
    private Long historiaClinicaId;
}