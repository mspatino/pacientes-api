package com.consultorio.pacientes.dtos;

import java.time.LocalDateTime;

import com.consultorio.pacientes.entities.enums.EstadoTurno;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TurnoDTO {

    private Long pacienteId; // opcional

    private String nombreContacto;
    private String telefonoContacto;
    @NotNull(message = "La fecha y hora del turno es obligatoria")
    private LocalDateTime fechaHoraInicio;
    private EstadoTurno estado;
    private Integer duracionMinutos;
    private String notas;

}
