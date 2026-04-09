package com.consultorio.pacientes.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.consultorio.pacientes.dtos.PacienteDTO;
import com.consultorio.pacientes.dtos.PacienteResponseDTO;
import com.consultorio.pacientes.entities.Paciente;

@Component
public class PacienteMapper {

    // DTO → Entity
    public static Paciente toEntity(PacienteDTO dto) {

        Paciente p = new Paciente();

        p.setNombre(dto.getNombre());
        p.setApellido(dto.getApellido());
        p.setDni(dto.getDni());
        p.setDireccion(dto.getDireccion());
        p.setTelefono(dto.getTelefono());
        p.setOcupacion(dto.getOcupacion());
        p.setNivelEducativo(dto.getNivelEducativo());
        p.setEstadoCivil(dto.getEstadoCivil());
        p.setSexo(dto.getSexo());
        p.setEmail(dto.getEmail());
        p.setConvivientes(dto.getConvivientes());
        p.setFechaNacimiento(dto.getFechaNacimiento());

        return p;
    }

    // Entity → ResponseDTO
    public static PacienteResponseDTO toDTO(Paciente p) {

        PacienteResponseDTO dto = new PacienteResponseDTO();

        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setApellido(p.getApellido());
        dto.setDni(p.getDni());
        dto.setDireccion(p.getDireccion());
        dto.setTelefono(p.getTelefono());
        dto.setOcupacion(p.getOcupacion());
        dto.setNivelEducativo(p.getNivelEducativo());
        dto.setEstadoCivil(p.getEstadoCivil());
        dto.setSexo(p.getSexo());
        dto.setEmail(p.getEmail());
        dto.setConvivientes(p.getConvivientes());
        dto.setFechaNacimiento(p.getFechaNacimiento());
        dto.setFechaAlta(p.getFechaAlta());

        // 👇 relación opcional
        if (p.getHistoriaClinica() != null) {
            dto.setHistoriaClinicaId(p.getHistoriaClinica().getId());
        }

        return dto;
    }

    public static List<PacienteResponseDTO> toDTOList(List<Paciente> lista) {
        return lista.stream().map(PacienteMapper::toDTO).toList();
    }
}
