package com.consultorio.pacientes.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.consultorio.pacientes.entities.Cie10;
import com.consultorio.pacientes.repositories.Cie10Repository;
import com.consultorio.pacientes.services.Cie10Service;

@Service
public class Cie10ServiceImpl implements Cie10Service{

    @Autowired
    private Cie10Repository repository;

    public List<Cie10> buscar(String texto){
        return repository.findTop20ByDescripcionContainingIgnoreCase(texto);
    }

    @Override
    public Page<Cie10> buscarPorCodigoYDescripcion(String texto,Integer page, Integer size) {
         //  return repository.buscar(texto, PageRequest.of(page,size));
       return repository.findByDescripcionContainingIgnoreCaseOrCodigoContainingIgnoreCase(
        texto, texto, PageRequest.of(page,size));
    }

    // @Override
    // public List<Cie10> autocomplete(String texto) {
    //     // Solo devuelve 10 diagnósticos.
    //      Pageable limit = PageRequest.of(0,10);
    //     return repository.autocomplete(texto.toLowerCase(), limit);
    // }
@Override
public List<Cie10> autocomplete(String texto) {

    if (texto == null || texto.trim().length() < 3) {
        return List.of();
    }

    Pageable limit = PageRequest.of(0, 10);

    texto = texto.trim().toLowerCase();
    List<String> palabras = List.of(texto.split("\\s+"));

    return repository.autocompleteFlexible(palabras, limit);
}

// @Override
// public List<Cie10> autocomplete(String texto, int page, int size) {

//     if (texto == null || texto.trim().length() < 3) {
//         return List.of();
//     }

//     Pageable pageable = PageRequest.of(page, size);

//     texto = texto.trim().toLowerCase();
//     List<String> palabras = List.of(texto.split("\\s+"));

//     return repository.autocompleteFlexible(palabras, pageable);
// }
@Override
public List<Cie10> autocomplete(String texto, int page, int size) {

    if (texto == null || texto.trim().length() < 3) {
        return List.of();
    }

    Pageable pageable = PageRequest.of(page, size);

    texto = texto.trim();
    String textoUpper = texto.toUpperCase();
    String textoLower = texto.toLowerCase();

    //  DETECTAR SI ES CODIGO
    if (textoUpper.matches("^[A-Z]\\d.*")) {

        // 1. exacto primero
        Optional<Cie10> exacto = repository.findById(textoUpper);
        if (exacto.isPresent()) {
            return List.of(exacto.get());
        }

        // 2. prefijo de código (F32, F3, etc.)
        return repository.buscarPorCodigo(textoUpper, pageable);
    }

    // 🔎 TEXTO NORMAL (multi palabra)
    List<String> palabras = List.of(textoLower.split("\\s+"));

    return repository.autocompleteFlexible(palabras, pageable);
}

    // @Override
    // public List<Cie10> autocomplete(String texto) {

    //    //Pageable limit = PageRequest.of(0, 10);

        
    //      texto = texto.trim().replaceAll("[^A-Za-z0-9.]", "");

    //     String textoUpper = texto.toUpperCase();
    //     String textoLower = texto.toLowerCase();
        
    //     // si empieza con letra + número → parece código CIE10
    //     //if (texto.matches("(?i)^F\\d.*")) {
    //      if (textoUpper.matches("^[A-Z]\\d.*")){
    //         // return repository.buscarPorCodigo(texto.toUpperCase(), limit);
    //         // 1 buscar exacto
    //         Optional<Cie10> exacto = repository.findById(textoUpper);
    //         if (exacto.isPresent()) {
    //             return List.of(exacto.get());
    //         }
    //         System.out.println("Busca por codigo");
    //         // 2 buscar prefijo
    //         return repository.buscarPorCod(textoUpper);

    //     }


    //     return repository.buscarPorDesc(textoLower);
    // }

}
