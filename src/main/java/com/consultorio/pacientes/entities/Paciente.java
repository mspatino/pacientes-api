package com.consultorio.pacientes.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.consultorio.pacientes.entities.enums.ConvivienteTipo;
import com.consultorio.pacientes.entities.enums.EstadoCivilTipo;
import com.consultorio.pacientes.entities.enums.NivelEducativoTipo;

import jakarta.persistence.Column;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Entity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @Enumerated(EnumType.STRING)
    @Column(length = 40, nullable = false)
    @NotNull(message = "El nivel educativo es obligatorio")
    private NivelEducativoTipo nivelEducativo;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    @NotNull(message = "El estado civil es obligatorio")
    private EstadoCivilTipo estadoCivil;

    @Column(length = 20)
    @Pattern(
        regexp = "^(Masculino|Femenino|Otro)$",
        message = "Sexo inválido. Valores permitidos: Masculino, Femenino, Otro"
    )
    private String sexo;

    @Email
    @Column(length = 150)
    private String email;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private LocalDateTime fechaAlta;

    @OneToOne(mappedBy = "paciente", fetch = FetchType.LAZY)
    @JsonIgnore
    private HistoriaClinica historiaClinica;

    @ElementCollection(targetClass = ConvivienteTipo.class)
    @CollectionTable(name = "paciente_convivientes", joinColumns = @JoinColumn(name = "paciente_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "conviviente")
    @NotEmpty(message = "Debe informar al menos un conviviente")
    private List<ConvivienteTipo> convivientes;

    @PrePersist
    public void prePersist() {
    this.fechaAlta = LocalDateTime.now();
    }

}
