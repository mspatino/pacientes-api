package com.consultorio.pacientes.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.pacientes.entities.Turno;
import com.consultorio.pacientes.entities.enums.EstadoTurno;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {

    List<Turno> findByFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(
            LocalDateTime desde,
            LocalDateTime hasta);

    List<Turno> findByEstadoOrderByFechaHoraInicioAsc(
            EstadoTurno estado);

    List<Turno> findByPacienteIdOrderByFechaHoraInicioDesc(
            Long pacienteId);

    boolean existsByFechaHoraInicio(
            LocalDateTime fechaHoraInicio);

    boolean existsByFechaHoraInicioAndPacienteId(
            LocalDateTime fechaHoraInicio,
            Long pacienteId);

    List<Turno> findByFechaHoraInicioAfterOrderByFechaHoraInicioAsc(
            LocalDateTime fecha);

    List<Turno> findByFechaHoraInicioBeforeOrderByFechaHoraInicioDesc(
            LocalDateTime fecha);
}
