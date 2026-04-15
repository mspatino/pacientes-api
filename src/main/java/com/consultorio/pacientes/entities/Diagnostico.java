package com.consultorio.pacientes.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "diagnostico",
    indexes = {
        @Index(name = "idx_historia", columnList = "historia_clinica_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_clinica_id", nullable = false)
    @JsonIgnore
    private HistoriaClinica historiaClinica;

    // opcional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cie10_codigo")
    private Cie10 cie10;

    @Column(nullable = false, length = 2000)
    private String descripcion;

    @Column(length = 4000)
    private String evolucion;

    @Column(length = 2000)
    private String tratamiento;

    @NotNull
    @Column(nullable = false)
    private Boolean principal;

    @Column(nullable = true)
    private LocalDateTime fechaInicio;

    @Column(nullable = true)
    private LocalDateTime fechaFin; // null = activo

    @PrePersist
    public void prePersist() {
        if (this.fechaInicio == null) {
            this.fechaInicio = LocalDateTime.now();
        }
        if (this.principal == null) {
            this.principal = Boolean.FALSE;
        }
    }

    

}
