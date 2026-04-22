package com.consultorio.pacientes.mapper;

import org.springframework.stereotype.Component;

import com.consultorio.pacientes.dtos.TurnoDTO;
import com.consultorio.pacientes.dtos.TurnoResponseDTO;

import com.consultorio.pacientes.entities.Turno;

@Component
public class TurnoMapper {

    public static Turno toEntity(TurnoDTO dto)  {
        Turno turno = new Turno();
        
        turno.setNombreContacto(dto.getNombreContacto());
        turno.setTelefonoContacto(dto.getTelefonoContacto());
        
        turno.setFechaHoraInicio(dto.getFechaHoraInicio());
        
        turno.setDuracionMinutos(
                dto.getDuracionMinutos() != null
                        ? dto.getDuracionMinutos()
                        : 45);
        turno.setNotas(dto.getNotas());
        return turno;
    }

    public static TurnoResponseDTO toDTO(Turno turno) {
        TurnoResponseDTO dto = new TurnoResponseDTO();
        dto.setId(turno.getId());
        if (turno.getPaciente() != null) {
            dto.setPacienteId(turno.getPaciente().getId());
            String nombreCompleto = (turno.getPaciente().getApellido() != null
                    ? turno.getPaciente().getApellido()
                    : "")
                    + " "
                    + (turno.getPaciente().getNombre() != null
                            ? turno.getPaciente().getNombre()
                            : "");
            dto.setPacienteNombre(nombreCompleto.trim());
        }
        dto.setNombreContacto(turno.getNombreContacto());
        dto.setTelefonoContacto(turno.getTelefonoContacto());
        dto.setFechaHoraInicio(turno.getFechaHoraInicio());
        dto.setDuracionMinutos(turno.getDuracionMinutos());
        dto.setFechaHoraFin(turno.getFechaHoraFin());
        dto.setEstado(turno.getEstado());
        dto.setNotas(turno.getNotas());
        return dto;

    }

}
