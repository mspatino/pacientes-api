package com.consultorio.pacientes.services.impl;

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

    // ================================
    // CREATE
    // ================================

    @Override
    public DiagnosticoResponseDTO crearDiagnostico(
            Long historiaClinicaId, DiagnosticoDTO d)
            {
        HistoriaClinica historia = historiaClinicaRepository
                .findByIdFull(historiaClinicaId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Historia clínica no encontrada")); 
                        
          // Validar diagnóstico principal único
        // if (Boolean.TRUE.equals(d.getPrincipal())) {
        //     boolean existePrincipal = diagnosticoRepository
        //             .existsByHistoriaClinicaIdAndPrincipalTrue(historiaClinicaId);

        //     if (existePrincipal) {
        //         throw new BusinessException("Ya existe un diagnóstico principal para esta historia clínica");
        //     }
        // }   

        validarDescripcionOCie10(d);
        Diagnostico diagnostico = buildDiagnostico(historia, d);
        Diagnostico saved = diagnosticoRepository.save(diagnostico); 


	        //lógica PRO: reemplaza automáticamente el principal
	        if (Boolean.TRUE.equals(d.getPrincipal())) {
	            diagnosticoRepository.setPrincipalUnico(historiaClinicaId, saved.getId());
	            // El update corre directo en DB, ajustamos el objeto para que el response sea consistente.
	            saved.setPrincipal(Boolean.TRUE);
	        }

        return DiagnosticoMapper.toDTO(saved);

    }

    public List<DiagnosticoResponseDTO> listarPorHistoria(Long historiaId) {
        List<Diagnostico> lista = diagnosticoRepository
                .findByHistoriaClinicaIdOrderByFechaInicioDesc(historiaId);

        return lista.stream()
                .map(DiagnosticoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DiagnosticoResponseDTO obtenerPrincipal(Long historiaId) {
        Diagnostico principal = diagnosticoRepository
        .findByHistoriaClinicaIdAndPrincipalTrueAndFechaFinIsNull(historiaId);
                //.findByHistoriaClinicaIdAndPrincipalTrue(historiaId);

        if (principal == null) {
            throw new ResourceNotFoundException("No hay diagnóstico principal para esta historia clínica");
        }

        return DiagnosticoMapper.toDTO(principal);
    }

    // ================================
    // DELETE
    // ================================
    @Override
    public void eliminarDiagnostico(Long diagnosticoId) {
        if (!diagnosticoRepository.existsById(diagnosticoId)) {
            throw new ResourceNotFoundException("Diagnóstico no encontrado");
        }
        diagnosticoRepository.deleteById(diagnosticoId);
    }

    // ================================
    // UPDATE
    // ================================
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

        // si lo quieren como principal → reemplaza
        if (Boolean.TRUE.equals(dto.getPrincipal())) {
            diagnosticoRepository.setPrincipalUnico(
                    diag.getHistoriaClinica().getId(),
                    diagnosticoId
            );
        }

        // CIE10 opcional
        if (dto.getCie10Codigo() != null && !dto.getCie10Codigo().isBlank()) {
            Cie10 cie10 = cie10Repository.findById(dto.getCie10Codigo())
                    .orElseThrow(() -> new ResourceNotFoundException("CIE10 no encontrado"));
            diag.setCie10(cie10);
        }

        Diagnostico updated = diagnosticoRepository.save(diag);

        return DiagnosticoMapper.toDTO(updated);
    }

    //**************/
    //** HELPERS **/
    //*************/

    private void validarDescripcionOCie10(DiagnosticoDTO dto) {
        if ((dto.getDescripcion() == null || dto.getDescripcion().isBlank())
                && (dto.getCie10Codigo() == null || dto.getCie10Codigo().isBlank())) {

            throw new BusinessException("Debe ingresar una descripción o un código CIE10");
        }
    }

    private Diagnostico buildDiagnostico(HistoriaClinica historia, DiagnosticoDTO dto) {

        Diagnostico diagnostico = new Diagnostico();

        diagnostico.setHistoriaClinica(historia);
        diagnostico.setDescripcion(dto.getDescripcion());
        diagnostico.setEvolucion(dto.getEvolucion());
        diagnostico.setTratamiento(dto.getTratamiento());

	        // siempre false por defecto
	        diagnostico.setPrincipal(Boolean.FALSE);

        if (dto.getCie10Codigo() != null && !dto.getCie10Codigo().isBlank()) {
            Cie10 cie10 = cie10Repository.findById(dto.getCie10Codigo())
                    .orElseThrow(() -> new ResourceNotFoundException("CIE10 no encontrado"));

            diagnostico.setCie10(cie10);

            if (diagnostico.getDescripcion() == null || diagnostico.getDescripcion().isBlank()) {
                diagnostico.setDescripcion(cie10.getDescripcion());
            }
        }

        historia.addDiagnostico(diagnostico);

        return diagnostico;
    }


}
