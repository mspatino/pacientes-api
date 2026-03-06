package com.consultorio.pacientes.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultorio.pacientes.dtos.HistoriaClinicaDTO;
import com.consultorio.pacientes.entities.HistoriaClinica;
import com.consultorio.pacientes.entities.Paciente;
import com.consultorio.pacientes.repositories.HistoriaClinicaRepository;
import com.consultorio.pacientes.repositories.PacienteRepository;
import com.consultorio.pacientes.services.HistoriaClinicaService;

import jakarta.persistence.EntityNotFoundException;



@Service
public class HistoriaClinicaServiceImpl implements HistoriaClinicaService {

    private final HistoriaClinicaRepository historiaRepo;
    private final PacienteRepository pacienteRepo;
    // private final Icd10Repository icd10Repo;

    public HistoriaClinicaServiceImpl(
            HistoriaClinicaRepository historiaRepo,
            PacienteRepository pacienteRepo) {

        this.historiaRepo = historiaRepo;
        this.pacienteRepo = pacienteRepo;
}

        public HistoriaClinica crearHistoria(Long pacienteId, HistoriaClinicaDTO dto) {

        Paciente paciente = pacienteRepo.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

          // Verificar si ya tiene historia clínica
        if (historiaRepo.existsByPacienteId(pacienteId)) {
            throw new RuntimeException("El paciente ya tiene historia clínica");
        }

        System.out.println("=== Datos del Paciente ===");
        System.out.println("ID: " + paciente.getId());
        System.out.println("Nombre: " + paciente.getNombre());
        System.out.println("Apellido: " + paciente.getApellido());
        System.out.println("DNI: " + paciente.getDni());
        System.out.println("Dirección: " + paciente.getDireccion());
        System.out.println("Teléfono: " + paciente.getTelefono());
        System.out.println("Ocupación: " + paciente.getOcupacion());
        System.out.println("Fecha de nacimiento: " + paciente.getFechaNacimiento());
        System.out.println("Fecha de alta: " + paciente.getFechaAlta());
        System.out.println("==========================");

        HistoriaClinica historia = new HistoriaClinica();
        historia.setPaciente(paciente);
        historia.setFechaAlta(LocalDateTime.now());
        historia.setActiva(true);

        // Campos del DTO
        historia.setMotivoConsulta(dto.getMotivoConsulta());
        historia.setObservaciones(dto.getObservaciones());    
          // default segura
        historia.setActiva(
            dto.getActiva() != null ? dto.getActiva() : true
        );
      

        return historiaRepo.save(historia);
    }

    @Transactional
    public HistoriaClinica actualizarHistoria(Long historiaId, HistoriaClinicaDTO dto) {

        HistoriaClinica historia = historiaRepo.findById(historiaId)
                .orElseThrow(() -> new RuntimeException("Historia clínica no encontrada"));

        boolean actualizado = false;                

        if (dto.getMotivoConsulta() != null) {
            historia.setMotivoConsulta(dto.getMotivoConsulta());
            actualizado = true;
        }

        if (dto.getObservaciones() != null) {
            historia.setObservaciones(dto.getObservaciones());
            actualizado = true;
        }

        if (dto.getActiva() != null) {
            historia.setActiva(dto.getActiva());
            actualizado = true;
        }

        if (!actualizado) {
            throw new IllegalArgumentException("No se enviaron campos para actualizar");
        }
        
        return historiaRepo.save(historia);
    }

    @Transactional
    public HistoriaClinica cerrarHistoria(Long historiaId) {

    HistoriaClinica historia = historiaRepo.findById(historiaId)
        .orElseThrow(() -> new EntityNotFoundException("Historia clínica no encontrada"));

    if (!historia.getActiva()) {
        throw new IllegalStateException("La historia clínica ya está cerrada");
    }

    historia.setActiva(false);
    return historiaRepo.save(historia);
    }

    
      // Obtener la historia clínica de un paciente
    public Optional<HistoriaClinica> obtenerHistoriaPorPaciente(Long pacienteId) {
        return historiaRepo.findByPacienteId(pacienteId);
    }

    public Optional<HistoriaClinica> obtenerPorId(Long id) {
        return historiaRepo.findById(id);
    }

}
