package com.consultorio.pacientes.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HistoriaClinicaDTO {

    @NotBlank(message = "El motivo de consulta es obligatorio")
    private String motivoConsulta;
    private String observaciones;
    private boolean activa; // wrapper para permitir null
    

}
