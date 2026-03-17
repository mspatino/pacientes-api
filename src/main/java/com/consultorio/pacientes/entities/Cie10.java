package com.consultorio.pacientes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cie10_catalogo")
public class Cie10 {

    @Id
    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "descripcion")
    private String descripcion;
  
    // getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

  
    
}

