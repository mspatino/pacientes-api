package com.consultorio.pacientes.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.consultorio.pacientes.entities.HistoriaClinica;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica,Long>, 
                JpaSpecificationExecutor<HistoriaClinica> {


    @Query("""
        SELECT h FROM HistoriaClinica h
        LEFT JOIN FETCH h.diagnosticos d
        LEFT JOIN FETCH d.cie10
        WHERE h.id = :id
    """)
    Optional<HistoriaClinica> findByIdFull(@Param("id") Long id);
    Optional<HistoriaClinica> findByPacienteId(Long pacienteId);

    boolean existsByPacienteId(Long pacienteId);

     
    Page<HistoriaClinica> findByActivaTrue(Pageable pageable);

     
     @Query("""
                 SELECT h
                 FROM HistoriaClinica h
                 JOIN h.paciente p
                 WHERE (:dni IS NULL OR p.dni = :dni)
                 AND (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
                 AND (:apellido IS NULL OR LOWER(p.apellido) LIKE LOWER(CONCAT('%', :apellido, '%')))
                 AND (:activa IS NULL OR h.activa = :activa)
                 AND (:desde IS NULL OR h.fechaAlta >= :desde)
                 AND (:hasta IS NULL OR h.fechaAlta <= :hasta)
             """)
     Page<HistoriaClinica> buscarHistorias(
             @Param("dni") String dni,
             @Param("nombre") String nombre,
             @Param("apellido") String apellido,
             @Param("activa") Boolean activa,
             @Param("desde") LocalDate desde,
             @Param("hasta") LocalDate hasta,
             Pageable pageable);

     @Query("SELECT h FROM HistoriaClinica h LEFT JOIN FETCH h.diagnosticos WHERE h.id = :id")
     Optional<HistoriaClinica> findByIdWithDiagnosticos(@Param("id") Long id);              

}
