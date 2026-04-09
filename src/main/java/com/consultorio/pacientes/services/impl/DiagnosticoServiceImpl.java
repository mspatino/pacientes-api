package com.consultorio.pacientes.services.impl;


import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.consultorio.pacientes.dtos.DiagnosticoDTO;
import com.consultorio.pacientes.dtos.DiagnosticoResponseDTO;
import com.consultorio.pacientes.dtos.DiagnosticoUpdateDTO;
import com.consultorio.pacientes.entities.Cie10;
import com.consultorio.pacientes.entities.Diagnostico;
import com.consultorio.pacientes.entities.HistoriaClinica;
import com.consultorio.pacientes.exception.BusinessException;
import com.consultorio.pacientes.exception.ResourceNotFoundException;
import com.consultorio.pacientes.mapper.DiagnosticoMapper;
import com.consultorio.pacientes.repositories.Cie10Repository;
import com.consultorio.pacientes.repositories.DiagnosticoRepository;
import com.consultorio.pacientes.repositories.HistoriaClinicaRepository;
import com.consultorio.pacientes.services.DiagnosticoService;

@Transactional
@Service
public class DiagnosticoServiceImpl implements DiagnosticoService{

    private final DiagnosticoRepository diagnosticoRepository;
    private final Cie10Repository cie10Repository;
    private final HistoriaClinicaRepository historiaClinicaRepository;

    public DiagnosticoServiceImpl(
            DiagnosticoRepository diagnosticoRepository,
            Cie10Repository cie10Repository,
            HistoriaClinicaRepository historiaClinicaRepository) {

        this.diagnosticoRepository = diagnosticoRepository;
        this.cie10Repository = cie10Repository;
        this.historiaClinicaRepository = historiaClinicaRepository;
    }

    @Override
    public DiagnosticoResponseDTO crearDiagnostico(
            Long historiaClinicaId, DiagnosticoDTO d)
            {

   

        HistoriaClinica historia = historiaClinicaRepository
                .findByIdFull(historiaClinicaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Historia clínica no encontrada")); 
                        
          // Validar diagnóstico principal único
        if (Boolean.TRUE.equals(d.getPrincipal())) {
            boolean existePrincipal = diagnosticoRepository
                    .existsByHistoriaClinicaIdAndPrincipalTrue(historiaClinicaId);

            if (existePrincipal) {
                throw new BusinessException("Ya existe un diagnóstico principal para esta historia clínica");
            }
        }   
        
        if ((d.getDescripcion() == null || d.getDescripcion().isBlank())
                && (d.getCie10Codigo() == null || d.getCie10Codigo().isBlank())) {

            throw new BusinessException("Debe ingresar una descripción o un código CIE10");
        }

        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setPrincipal(Boolean.TRUE.equals(d.getPrincipal()));
        diagnostico.setHistoriaClinica(historia);

        if (d.getDescripcion() != null && !d.getDescripcion().isBlank()){
            diagnostico.setDescripcion(d.getDescripcion());
        }

        diagnostico.setEvolucion(d.getEvolucion());
        diagnostico.setTratamiento(d.getTratamiento());

        // CIE10 opcional
        String cie10Codigo = d.getCie10Codigo();
        if (cie10Codigo != null && !cie10Codigo.isBlank()) {

            Cie10 cie10 = cie10Repository
                    .findById(cie10Codigo)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("CIE10 no encontrado"));

            diagnostico.setCie10(cie10);
        }
        
        historia.addDiagnostico(diagnostico);

        Diagnostico saved = diagnosticoRepository.save(diagnostico); 

        return DiagnosticoMapper.toDTO(saved);

    }

    
    public Diagnostico crearDiagnosticoEntity(HistoriaClinica historia, DiagnosticoDTO d) {

        if (Boolean.TRUE.equals(d.getPrincipal())) {
            boolean existePrincipal = diagnosticoRepository
                    .existsByHistoriaClinicaIdAndPrincipalTrue(historia.getId());

            if (existePrincipal) {
                throw new BusinessException("Ya existe un diagnóstico principal");
            }
        }

        if ((d.getDescripcion() == null || d.getDescripcion().isBlank())
                && (d.getCie10Codigo() == null || d.getCie10Codigo().isBlank())) {

            throw new BusinessException("Debe ingresar una descripción o CIE10");
        }

        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setHistoriaClinica(historia);
        diagnostico.setDescripcion(d.getDescripcion());
        diagnostico.setEvolucion(d.getEvolucion());
        diagnostico.setTratamiento(d.getTratamiento());
        diagnostico.setPrincipal(Boolean.TRUE.equals(d.getPrincipal()));

        if (d.getCie10Codigo() != null && !d.getCie10Codigo().isBlank()) {
            Cie10 cie10 = cie10Repository.findById(d.getCie10Codigo())
                    .orElseThrow(() -> new ResourceNotFoundException("CIE10 no encontrado"));
            diagnostico.setCie10(cie10);
        }

        historia.addDiagnostico(diagnostico);

        return diagnosticoRepository.save(diagnostico);
    }

    public List<DiagnosticoResponseDTO> listarPorHistoria(Long historiaId) {
        List<Diagnostico> lista = diagnosticoRepository
                .findByHistoriaClinicaIdOrderByFechaDesc(historiaId);

        return lista.stream()
                .map(DiagnosticoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DiagnosticoResponseDTO obtenerPrincipal(Long historiaId) {
        Diagnostico principal = diagnosticoRepository
                .findByHistoriaClinicaIdAndPrincipalTrue(historiaId);

        if (principal == null) {
            throw new ResourceNotFoundException("No hay diagnóstico principal para esta historia clínica");
        }

        return DiagnosticoMapper.toDTO(principal);
    }

    @Override
    public DiagnosticoResponseDTO actualizarDiagnostico(Long diagnosticoId, DiagnosticoUpdateDTO dto) {
        Diagnostico diag = diagnosticoRepository.findById(diagnosticoId)
                .orElseThrow(() -> new ResourceNotFoundException("Diagnóstico no encontrado"));

        if (dto.getDescripcion() != null)
            diag.setDescripcion(dto.getDescripcion());
        if (dto.getEvolucion() != null)
            diag.setEvolucion(dto.getEvolucion());
        if (dto.getTratamiento() != null)
            diag.setTratamiento(dto.getTratamiento());

        
            if (Boolean.TRUE.equals(dto.getPrincipal()) &&
                    diagnosticoRepository.existsByHistoriaClinicaIdAndPrincipalTrue(diag.getHistoriaClinica().getId())
                    && !Boolean.TRUE.equals(diag.getPrincipal())) {
                throw new ResourceNotFoundException("Ya existe un diagnóstico principal para esta historia clínica");
            }
            if (dto.getPrincipal() != null) {
                diag.setPrincipal(dto.getPrincipal());
            }
        

        // Actualizar CIE10 si se proporciona
        if (dto.getCie10Codigo() != null && !dto.getCie10Codigo().isBlank()) {
            Cie10 cie10 = cie10Repository.findById(dto.getCie10Codigo())
                    .orElseThrow(() -> new ResourceNotFoundException("CIE10 no encontrado"));
            diag.setCie10(cie10);
        }

        Diagnostico updated = diagnosticoRepository.save(diag);
        return DiagnosticoMapper.toDTO(updated);
    }

    @Override
    public void eliminarDiagnostico(Long diagnosticoId) {
        if (!diagnosticoRepository.existsById(diagnosticoId)) {
            throw new ResourceNotFoundException("Diagnóstico no encontrado");
        }
        diagnosticoRepository.deleteById(diagnosticoId);
    }


}


