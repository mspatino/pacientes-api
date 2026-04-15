package com.consultorio.pacientes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.consultorio.pacientes.entities.Diagnostico;

@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico,Long>{

    List<Diagnostico> findByHistoriaClinicaId(Long historiaClinicaId);

    List<Diagnostico> findByHistoriaClinicaIdOrderByFechaInicioDesc(Long historiaClinicaId);

    Diagnostico findByHistoriaClinicaIdAndPrincipalTrue(Long historiaClinicaId);
    
    Diagnostico findByHistoriaClinicaIdAndPrincipalTrueAndFechaFinIsNull(Long historiaId);

    boolean existsByHistoriaClinicaIdAndPrincipalTrue(Long historiaId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE Diagnostico d
            SET d.principal = false
            WHERE d.historiaClinica.id = :historiaId
            """)
    void clearPrincipalByHistoria(@Param("historiaId") Long historiaId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                UPDATE Diagnostico d
                SET d.principal = CASE
                    WHEN d.id = :diagnosticoId THEN true
                    ELSE false
                END
                WHERE d.historiaClinica.id = :historiaId
            """)
    void setPrincipalUnico(
            @Param("historiaId") Long historiaId,
            @Param("diagnosticoId") Long diagnosticoId);

}
