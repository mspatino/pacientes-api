package com.consultorio.pacientes.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiagnosticoDTO {

    @Size(max = 2000)
    private String descripcion;

    @Size(max = 4000)
    private String evolucion;

    @Size(max = 2000)
    private String tratamiento;

    private Cie10DTO cie10;

    @NotNull(message = "Debe indicar si el diagnóstico es principal")
    private Boolean principal;

    private LocalDateTime fechaFin; 
}
  

