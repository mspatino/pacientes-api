package com.consultorio.pacientes.dtos;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiagnosticoUpdateDTO {

    @Size(max = 2000)
    private String descripcion;

    @Size(max = 4000)
    private String evolucion;

    @Size(max = 2000)
    private String tratamiento;

    private String cie10Codigo;

    private boolean principal;
}