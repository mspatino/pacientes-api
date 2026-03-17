package com.consultorio.pacientes.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.consultorio.pacientes.entities.Cie10;

public interface Cie10Service {

     public List<Cie10> buscar(String texto);
     public Page<Cie10> buscarPorCodigoYDescripcion(String texto,Integer page, Integer size);
     public List<Cie10> autocomplete(String texto);

}
