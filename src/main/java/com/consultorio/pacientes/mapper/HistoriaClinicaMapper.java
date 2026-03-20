package com.consultorio.pacientes.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.consultorio.pacientes.dtos.Cie10DTO;
import com.consultorio.pacientes.dtos.DiagnosticoResponseDTO;
import com.consultorio.pacientes.dtos.HistoriaClinicaResponseDTO;
import com.consultorio.pacientes.entities.HistoriaClinica;

@Component
public class HistoriaClinicaMapper {

        public static HistoriaClinicaResponseDTO toDTO(HistoriaClinica h) {

                List<DiagnosticoResponseDTO> lista = h.getDiagnosticos().stream().map(d -> {
                        DiagnosticoResponseDTO diag = new DiagnosticoResponseDTO();
                        diag.setDescripcion(d.getDescripcion());
                        diag.setEvolucion(d.getEvolucion());
                        diag.setTratamiento(d.getTratamiento());
                        diag.setPrincipal(d.isPrincipal());
                        diag.setId(d.getId());
                        diag.setFecha(d.getFecha());

                        // if (d.getCie10() != null) {
                        //         diag.setCie10Codigo(d.getCie10().getCodigo());
                        //          diag.setCie10Descripcion(d.getCie10().getDescripcion());
                        // }
                        if (d.getCie10() != null) {
                           Cie10DTO cie10DTO = new Cie10DTO();
                           cie10DTO.setCodigo(d.getCie10().getCodigo());
                           cie10DTO.setDescripcion(d.getCie10().getDescripcion());
                           diag.setCie10(cie10DTO);
                        }
                           
                        diag.setHistoriaClinicaId(h.getId());

                        return diag;
                }).toList();

                HistoriaClinicaResponseDTO dto = new HistoriaClinicaResponseDTO();

                dto.setId(h.getId());
                dto.setPacienteId(h.getPaciente().getId());
                dto.setFechaAlta(h.getFechaAlta());
                dto.setMotivoConsulta(h.getMotivoConsulta());
                dto.setObservaciones(h.getObservaciones());
                dto.setActiva(h.isActiva());

                // ACÁ USÁS LA LISTA CORRECTA
                dto.setDiagnosticos(lista);

                return dto;
        }

}
