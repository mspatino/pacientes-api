package com.consultorio.pacientes.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "historiaClinica"})
public class Paciente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(length = 20, unique = true)
    private String dni;

    @Column(length = 255)
    private String direccion;

    @Column(length = 50)
    private String telefono;

    @Column(length = 100)
    private String ocupacion;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private LocalDateTime fechaAlta;

    @OneToOne(mappedBy = "paciente", fetch = FetchType.LAZY)
    @JsonIgnore
    private HistoriaClinica historiaClinica;

    @PrePersist
    public void prePersist() {
    this.fechaAlta = LocalDateTime.now();
    }

}
