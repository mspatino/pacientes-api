package com.consultorio.pacientes.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultorio.pacientes.dtos.HistoriaClinicaDTO;
import com.consultorio.pacientes.dtos.HistoriaClinicaResponseDTO;
import com.consultorio.pacientes.entities.HistoriaClinica;
import com.consultorio.pacientes.entities.Paciente;
import com.consultorio.pacientes.exception.BusinessException;
import com.consultorio.pacientes.exception.ResourceNotFoundException;
import com.consultorio.pacientes.mapper.HistoriaClinicaMapper;
import com.consultorio.pacientes.repositories.HistoriaClinicaRepository;
import com.consultorio.pacientes.repositories.PacienteRepository;
import com.consultorio.pacientes.services.HistoriaClinicaService;
import com.consultorio.pacientes.specifications.HistoriaClinicaSpecification;

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


    public HistoriaClinicaResponseDTO crearHistoriaClinica(Long pacienteId, HistoriaClinicaDTO dto) {

    Paciente paciente = pacienteRepo.findById(pacienteId)
            .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

    // VALIDACIÓN CLAVE: para evitar explotar con excepción SQL 
    // no es una respuesta limpia para la API
    if (historiaRepo.existsByPacienteId(pacienteId)) {
        throw new BusinessException("El paciente ya tiene una historia clínica");
    }
    HistoriaClinica historia = new HistoriaClinica();
    historia.setPaciente(paciente);
    //historia.setFechaAlta(LocalDateTime.now());
    historia.setMotivoConsulta(dto.getMotivoConsulta());
    historia.setObservaciones(dto.getObservaciones());
    historia.setActiva(dto.getActiva() != null ? dto.getActiva() : true);

    HistoriaClinica saved = historiaRepo.save(historia);

    return HistoriaClinicaMapper.toDTO(saved);
}


      // Obtener la historia clínica de un paciente
    public Optional<HistoriaClinica> obtenerHistoriaPorPaciente(Long pacienteId) {
        return historiaRepo.findByPacienteId(pacienteId);
    }

    public Optional<HistoriaClinica> obtenerPorId(Long id) {
        return historiaRepo.findById(id);
    }


    @Override
    public Page<HistoriaClinica> buscarHistorias(String dni, String nombre, String apellido, Boolean activa,
            LocalDate desde, LocalDate hasta, Pageable pageable) {
    return historiaRepo.buscarHistorias(dni, nombre, apellido, activa, desde, hasta, pageable);
    }

    
    
    @Override
    public Page<HistoriaClinica> buscar(
            String dni,
            String nombre,
            String apellido,
            Boolean activa,
            LocalDate desde,
            LocalDate hasta,
            Pageable pageable) {

        Specification<HistoriaClinica> spec = Specification
                .where(HistoriaClinicaSpecification.dniPaciente(dni))
                .and(HistoriaClinicaSpecification.nombrePaciente(nombre))
                .and(HistoriaClinicaSpecification.apellidoPaciente(apellido))
                .and(HistoriaClinicaSpecification.activa(activa))
                .and(HistoriaClinicaSpecification.fechaDesde(desde))
                .and(HistoriaClinicaSpecification.fechaHasta(hasta));

        return historiaRepo.findAll(spec, pageable);
    }

    @Override
    public Page<HistoriaClinica> listarActivas(int page, int size) {
       Pageable pageable = PageRequest.of(page, size);

        return historiaRepo.findByActivaTrue(pageable);
        
    }

    @Transactional
    public HistoriaClinicaResponseDTO actualizarHistoriaClinica(Long historiaId, HistoriaClinicaDTO dto) {
           
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

    HistoriaClinica saved = historiaRepo.save(historia);

    return HistoriaClinicaMapper.toDTO(saved);
    }

    @Transactional
    public HistoriaClinicaResponseDTO cerrarHistoriaClinica(Long historiaId) {
        HistoriaClinica historia = historiaRepo.findById(historiaId)
                .orElseThrow(() -> new EntityNotFoundException("Historia clínica no encontrada"));

        if (!historia.getActiva()) {
            throw new IllegalStateException("La historia clínica ya está cerrada");
        }

        historia.setActiva(false);

        HistoriaClinica saved = historiaRepo.save(historia);
        return HistoriaClinicaMapper.toDTO(saved);
    }





}
