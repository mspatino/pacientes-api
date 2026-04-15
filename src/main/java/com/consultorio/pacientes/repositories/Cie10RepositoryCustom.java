package com.consultorio.pacientes.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.consultorio.pacientes.entities.Cie10;

public interface Cie10RepositoryCustom {
    List<Cie10> autocompleteFlexible(List<String> palabras, Pageable pageable);
}