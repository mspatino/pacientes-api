package com.consultorio.pacientes.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.consultorio.pacientes.entities.Cie10;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class Cie10RepositoryImpl implements Cie10RepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Cie10> autocompleteFlexible(List<String> palabras, Pageable pageable) {

        StringBuilder jpql = new StringBuilder("SELECT c FROM Cie10 c WHERE 1=1 ");

        for (int i = 0; i < palabras.size(); i++) {
            jpql.append(" AND LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :p" + i + ", '%')) ");
        }

        jpql.append("""
            ORDER BY 
                CASE 
                    WHEN LOWER(c.descripcion) LIKE LOWER(CONCAT(:p0, '%')) THEN 0
                    ELSE 1
                END,
                LENGTH(c.descripcion)
        """);

        var query = entityManager.createQuery(jpql.toString(), Cie10.class);

        for (int i = 0; i < palabras.size(); i++) {
            query.setParameter("p" + i, palabras.get(i));
        }

        query.setMaxResults(pageable.getPageSize());

        //offset
        query.setFirstResult((int) pageable.getOffset());   // desde qué fila empezar
        query.setMaxResults(pageable.getPageSize()); 

        return query.getResultList();
    }
}