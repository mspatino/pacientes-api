package com.consultorio.pacientes.services;

import java.time.LocalDate;
import java.util.List;

import com.consultorio.pacientes.dtos.TurnoDTO;
import com.consultorio.pacientes.dtos.TurnoResponseDTO;
import com.consultorio.pacientes.entities.enums.EstadoTurno;


public interface TurnoService {
    
    TurnoResponseDTO crear(TurnoDTO dto);

    TurnoResponseDTO actualizar(Long id, TurnoDTO dto);

    TurnoResponseDTO obtenerPorId(Long id);

    List<TurnoResponseDTO> getTurnosDelDia(LocalDate fecha);

    List<TurnoResponseDTO> getTurnosDeLaSemana(LocalDate fecha);

    List<TurnoResponseDTO> getTurnosDelMes(int anio, int mes);

    void cancelar(Long id);

    TurnoResponseDTO cambiarEstado(Long id, EstadoTurno estado);
}
