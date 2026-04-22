package com.consultorio.pacientes.dtos;

import java.time.LocalDateTime;

import com.consultorio.pacientes.entities.enums.EstadoTurno;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TurnoResponseDTO {
    private Long id;
    private Long pacienteId; // opcional
    private String pacienteNombre;

    private String nombreContacto;
    private String telefonoContacto;
    private EstadoTurno estado;
    private LocalDateTime fechaHoraInicio;
    //fechaHoraFin = fechaHoraInicio + duración
    private LocalDateTime fechaHoraFin;
    private Integer duracionMinutos;
    private String notas;

}