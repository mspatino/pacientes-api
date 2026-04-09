package com.consultorio.pacientes.dtos;

import java.util.List;

import jakarta.validation.Valid;
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
    private Boolean activa; // wrapper para permitir null
    private String medicacion;
    private String consumo;
    private String tratamientosAnteriores;
    private String observaciones;
    
    @Valid
    private List<DiagnosticoDTO> diagnosticos;

}
