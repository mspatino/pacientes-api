package com.consultorio.pacientes.services.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultorio.pacientes.dtos.DiagnosticoDTO;
import com.consultorio.pacientes.dtos.HistoriaClinicaDTO;
import com.consultorio.pacientes.dtos.HistoriaClinicaResponseDTO;
import com.consultorio.pacientes.entities.HistoriaClinica;
import com.consultorio.pacientes.entities.Paciente;
import com.consultorio.pacientes.exception.BusinessException;
import com.consultorio.pacientes.exception.ResourceNotFoundException;
import com.consultorio.pacientes.mapper.HistoriaClinicaMapper;
import com.consultorio.pacientes.repositories.HistoriaClinicaRepository;
import com.consultorio.pacientes.repositories.PacienteRepository;
import com.consultorio.pacientes.services.DiagnosticoService;
import com.consultorio.pacientes.services.HistoriaClinicaService;
import com.consultorio.pacientes.specifications.HistoriaClinicaSpecification;

import jakarta.persistence.EntityNotFoundException;


@Transactional
@Service
public class HistoriaClinicaServiceImpl implements HistoriaClinicaService {

    private final HistoriaClinicaRepository historiaRepo;
    private final PacienteRepository pacienteRepo;
    // private final Icd10Repository icd10Repo;
    private final DiagnosticoService diagnosticoService;



    public HistoriaClinicaServiceImpl(
            HistoriaClinicaRepository historiaRepo,
            PacienteRepository pacienteRepo,
            DiagnosticoService diagnosticoService
           ) {

        this.historiaRepo = historiaRepo;
        this.pacienteRepo = pacienteRepo;
        this.diagnosticoService = diagnosticoService;
        
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
        // historia.setFechaAlta(LocalDateTime.now());
        historia.setMotivoConsulta(dto.getMotivoConsulta());
        historia.setObservaciones(dto.getObservaciones());
        historia.setMedicacion(dto.getMedicacion());
        historia.setConsumo(dto.getConsumo());
        historia.setTratamientosAnteriores(dto.getTratamientosAnteriores());
        historia.setActiva(dto.getActiva() != null ? dto.getActiva() : Boolean.TRUE);

        //guardar la historia clinica primero
        HistoriaClinica saved = historiaRepo.save(historia);
        
         //  ACÁ CREÁS LOS DIAGNÓSTICOS
        if (dto.getDiagnosticos() != null && !dto.getDiagnosticos().isEmpty()) {

        for (DiagnosticoDTO d : dto.getDiagnosticos()) {
            // reutilizás lógica, llamo al crearDiagnostico de DiagnosticoService
            diagnosticoService.crearDiagnostico(saved.getId(), d); 
        }
    }

    return HistoriaClinicaMapper.toDTO(saved);
    }



      // Obtener la historia clínica de un paciente
    public Optional<HistoriaClinicaResponseDTO> obtenerHistoriaPorPaciente(Long pacienteId) {
        //return historiaRepo.findByPacienteId(pacienteId);
        return historiaRepo.findByPacienteId(pacienteId)
            .map(HistoriaClinicaMapper::toDTO);
    }

    public HistoriaClinicaResponseDTO obtenerPorId(Long id) {
        HistoriaClinica hc = historiaRepo.findByIdFull(id)
        .orElseThrow(() -> new ResourceNotFoundException("Historia clínica no encontrada"));
        
        return HistoriaClinicaMapper.toDTO(hc);
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

    if (dto.getMedicacion() != null) {
        historia.setMedicacion(dto.getMedicacion());
        actualizado = true;
    }

    if (dto.getConsumo() != null) {
        historia.setConsumo(dto.getConsumo());
        actualizado = true;
    }

    if (dto.getTratamientosAnteriores() != null) {
        historia.setTratamientosAnteriores(dto.getTratamientosAnteriores());
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

        if (!Boolean.TRUE.equals(historia.getActiva())) {
            throw new IllegalStateException("La historia clínica ya está cerrada");
        }

        historia.setActiva(Boolean.FALSE);

        HistoriaClinica saved = historiaRepo.save(historia);
        return HistoriaClinicaMapper.toDTO(saved);
    }





}
