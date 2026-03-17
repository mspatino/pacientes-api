package com.consultorio.pacientes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.consultorio.pacientes.entities.Cie10;
import com.consultorio.pacientes.services.Cie10Service;

@RestController
@RequestMapping("/api/diagnosticos")
public class Cie10Controller {

    @Autowired
    private Cie10Service service;
//GET /api/diagnosticos/buscar?q=ansiedad
    @GetMapping("/buscar")
    public List<Cie10> buscar(@RequestParam String q){
        return service.buscar(q);
    }


    ///buscar-paginado?q=depresion&page=0&size=10
     @GetMapping("/buscar-paginado")
    public Page<Cie10> buscar(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size){

        return service.buscarPorCodigoYDescripcion(q, page, size);
    }


    // GET /api/diagnosticos/autocomplete?q=depre
    @GetMapping("/autocomplete")
    public List<Cie10> autocomplete(@RequestParam String q){
        return service.autocomplete(q);
    }
    
}
