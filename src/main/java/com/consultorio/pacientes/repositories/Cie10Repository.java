package com.consultorio.pacientes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.consultorio.pacientes.entities.Cie10;



@Repository
public interface Cie10Repository extends JpaRepository<Cie10,String>, Cie10RepositoryCustom {

   
    Optional<Cie10> findById(String texto);

    List<Cie10> findTop20ByDescripcionContainingIgnoreCase(String texto);

    // @Query("""
    //         SELECT c
    //         FROM Cie10 c
    //         WHERE LOWER(c.descripcion) LIKE LOWER(CONCAT('%',:texto,'%'))
    //         OR LOWER(c.codigo) LIKE LOWER(CONCAT('%',:texto,'%'))
    //         """)
    // List<Cie10> buscar(@Param("texto") String texto);

    @Query("""
            SELECT c
            FROM Cie10 c
            WHERE LOWER(c.descripcion) LIKE LOWER(CONCAT('%',:texto,'%'))
            OR LOWER(c.codigo) LIKE LOWER(CONCAT(:texto,'%'))
            """)
    Page<Cie10> buscar(@Param("texto") String texto, Pageable pageable);

    Page<Cie10> findByDescripcionContainingIgnoreCaseOrCodigoContainingIgnoreCase(
        String descripcion,
        String codigo,
        Pageable pageable);

    @Query("""
            SELECT c
            FROM Cie10 c
            WHERE 
                LOWER(c.descripcion) LIKE LOWER(CONCAT('%',:texto,'%'))
                OR LOWER(c.codigo) LIKE LOWER(CONCAT(:texto,'%'))
            """)
     List<Cie10> autocomplete(@Param("texto") String texto);

     


     @Query("""
             SELECT c
             FROM Cie10 c
             WHERE LOWER(c.descripcion) LIKE LOWER(CONCAT('%',:texto,'%'))
             ORDER BY c.descripcion
             """)
     List<Cie10> buscarPorDescripcion(@Param("texto") String texto, Pageable pageable);

     @Query("""
             SELECT c
             FROM Cie10 c
             WHERE c.codigo LIKE CONCAT('%',:codigo,'%')
             ORDER BY c.codigo
             """)
     List<Cie10> buscarPorCodigo(@Param("codigo") String codigo, Pageable pageable);

}
