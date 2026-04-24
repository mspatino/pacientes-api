package com.consultorio.pacientes.services.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultorio.pacientes.dtos.TurnoDTO;
import com.consultorio.pacientes.dtos.TurnoResponseDTO;
import com.consultorio.pacientes.entities.Paciente;
import com.consultorio.pacientes.entities.Turno;
import com.consultorio.pacientes.entities.enums.EstadoTurno;
import com.consultorio.pacientes.exception.BusinessException;
import com.consultorio.pacientes.exception.ResourceNotFoundException;
import com.consultorio.pacientes.mapper.TurnoMapper;
import com.consultorio.pacientes.repositories.PacienteRepository;
import com.consultorio.pacientes.repositories.TurnoRepository;
import com.consultorio.pacientes.services.TurnoService;

@Service
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepository turnoRepository;
    private final PacienteRepository pacienteRepository;

    public TurnoServiceImpl(TurnoRepository turnoRepository, PacienteRepository pacienteRepository) {
        this.turnoRepository = turnoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public TurnoResponseDTO crear(TurnoDTO dto) {
        validarContactoOTPaciente(dto);
        validarSuperposicion(dto.getFechaHoraInicio(),dto.getDuracionMinutos(),null);
        Turno turno = TurnoMapper.toEntity(dto);
        // Resolver paciente si viene pacienteId
        if (dto.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
            turno.setPaciente(paciente);
        }
        // Estado por defecto
        turno.setEstado(EstadoTurno.PENDIENTE);
        Turno turnoSaved = turnoRepository.save(turno);
        return TurnoMapper.toDTO(turnoSaved);
    }

    @Override
    public TurnoResponseDTO actualizar(Long id, TurnoDTO dto) {
        validarContactoOTPaciente(dto);
        validarSuperposicion(dto.getFechaHoraInicio(),dto.getDuracionMinutos(),id);
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
        Paciente paciente = null;

        if (dto.getPacienteId() != null) {
            paciente = pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
        }
        turno.setPaciente(paciente);
        turno.setNombreContacto(dto.getNombreContacto());
        turno.setTelefonoContacto(dto.getTelefonoContacto());
        turno.setFechaHoraInicio(dto.getFechaHoraInicio());
        turno.setDuracionMinutos(
                dto.getDuracionMinutos() != null
                        ? dto.getDuracionMinutos()
                        : 45);

        turno.setNotas(dto.getNotas());
        turno.setEstado(dto.getEstado() != null ? dto.getEstado() : turno.getEstado());
        Turno turnoSaved =  turnoRepository.save(turno);
        return TurnoMapper.toDTO(turnoSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public TurnoResponseDTO obtenerPorId(Long id) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
        return TurnoMapper.toDTO(turno);                
    }

    @Override
    public List<TurnoResponseDTO> getTurnosDelDia(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(LocalTime.MAX);

        List<Turno> turnos = turnoRepository
                .findByFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(
                        inicio,
                        fin);

        return turnos.stream()
                .map(TurnoMapper::toDTO)
                .toList();
    }

    @Override
    public List<TurnoResponseDTO> getTurnosDeLaSemana(LocalDate fecha) {

        LocalDate inicioSemana = fecha.with(DayOfWeek.MONDAY);
        LocalDate finSemana = fecha.with(DayOfWeek.SUNDAY);

        LocalDateTime inicio = inicioSemana.atStartOfDay();
        LocalDateTime fin = finSemana.atTime(LocalTime.MAX);

        List<Turno> turnos = turnoRepository
                .findByFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(
                        inicio,
                        fin);
        return turnos.stream().map(TurnoMapper::toDTO).toList();                        
    }

    @Override
    public List<TurnoResponseDTO> getTurnosDelMes(int anio, int mes) {
        YearMonth yearMonth = YearMonth.of(anio, mes);

        LocalDate inicioMes = yearMonth.atDay(1);
        LocalDate finMes = yearMonth.atEndOfMonth();

        LocalDateTime inicio = inicioMes.atStartOfDay();
        LocalDateTime fin = finMes.atTime(LocalTime.MAX);

         List<Turno> turnos = turnoRepository
                .findByFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(
                        inicio,
                        fin);
        return turnos.stream().map(TurnoMapper::toDTO).toList();
    }

    @Override
    public void cancelar(Long id) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
        turno.setEstado(EstadoTurno.CANCELADO);
        turnoRepository.save(turno);
    }

    private void validarContactoOTPaciente(TurnoDTO dto) {
        boolean tienePaciente = dto.getPacienteId() != null;
        boolean tieneContacto = dto.getNombreContacto() != null &&
                !dto.getNombreContacto().trim().isEmpty();
        if (!tienePaciente && !tieneContacto) {
            throw new BusinessException(
                    "Debe seleccionar un paciente o ingresar un nombre de contacto");
        }
    }

    private void validarSuperposicion(
            LocalDateTime inicioNuevo,
            Integer duracionMinutos,
            Long turnoIdExcluir) {

        LocalDateTime finNuevo = inicioNuevo.plusMinutes(
                duracionMinutos != null ? duracionMinutos : 45);

        List<Turno> turnosDelDia = turnoRepository
                .findByFechaHoraInicioBetweenOrderByFechaHoraInicioAsc(
                        inicioNuevo.toLocalDate().atStartOfDay(),
                        inicioNuevo.toLocalDate().atTime(LocalTime.MAX));

        boolean hayConflicto = turnosDelDia.stream()
                .filter(t -> turnoIdExcluir == null || !t.getId().equals(turnoIdExcluir))
                .filter(t -> t.getEstado() != EstadoTurno.CANCELADO)
                .anyMatch(turno -> {

                    LocalDateTime inicioExistente = turno.getFechaHoraInicio();

                    LocalDateTime finExistente = inicioExistente.plusMinutes(
                            turno.getDuracionMinutos() != null
                                    ? turno.getDuracionMinutos()
                                    : 45);

                    return inicioNuevo.isBefore(finExistente)
                            && finNuevo.isAfter(inicioExistente);
                });

        if (hayConflicto) {
            throw new BusinessException(
                    "Ya existe un turno en ese rango horario");
        }
    }

    @Override
    public TurnoResponseDTO cambiarEstado(Long id, EstadoTurno estado) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
        validarCambioEstado(turno.getEstado(), estado);
        turno.setEstado(estado);
        Turno turnoSaved = turnoRepository.save(turno);
        return TurnoMapper.toDTO(turnoSaved);
    }

    private void validarCambioEstado(EstadoTurno actual, EstadoTurno nuevo) {
        if (actual == nuevo) {
           return;
        }
        boolean valido = switch (actual) {
            case PENDIENTE ->
                nuevo == EstadoTurno.CONFIRMADO
                        || nuevo == EstadoTurno.CANCELADO;
            case CONFIRMADO ->
                nuevo == EstadoTurno.AUSENTE
                        || nuevo == EstadoTurno.CANCELADO;
            case CANCELADO,
                    AUSENTE ->
                false;
        };

        if (!valido) {
            throw new BusinessException(
                    "No se puede cambiar el estado de "+ actual+ " a "+ nuevo);
        }
    }



}
