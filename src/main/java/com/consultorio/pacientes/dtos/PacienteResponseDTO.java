package com.consultorio.pacientes.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteResponseDTO {

    private Long id;

    private String nombre;
    private String apellido;
    private String dni;
    private String direccion;
    private String telefono;
    private String ocupacion;
    private String sexo;
    private String email;

    private LocalDate fechaNacimiento;
    private LocalDateTime fechaAlta;

    // 👇 opcional pero MUY útil
    private Long historiaClinicaId;
}
