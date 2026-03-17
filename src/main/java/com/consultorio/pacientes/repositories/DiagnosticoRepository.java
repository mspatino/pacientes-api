package com.consultorio.pacientes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.consultorio.pacientes.entities.Diagnostico;

@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico,Long>{

    List<Diagnostico> findByHistoriaClinicaId(Long historiaClinicaId);

    List<Diagnostico> findByHistoriaClinicaIdOrderByFechaDesc(Long historiaClinicaId);

    Diagnostico findByHistoriaClinicaIdAndPrincipalTrue(Long historiaClinicaId);

    boolean existsByHistoriaClinicaIdAndPrincipalTrue(Long historiaId);

}
