package com.consultorio.pacientes.dtos;

import java.time.LocalDate;
import java.util.List;

import com.consultorio.pacientes.entities.enums.ConvivienteTipo;
import com.consultorio.pacientes.entities.enums.EstadoCivilTipo;
import com.consultorio.pacientes.entities.enums.NivelEducativoTipo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PacienteDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe tener 7 u 8 dígitos")
    private String dni;

    @Size(max = 255, message = "La dirección es demasiado larga")
    private String direccion;

    // @Pattern(regexp = "\\d{6,15}", message = "Teléfono inválido")
    @Pattern(regexp = "^[0-9+\\-\\s]{6,20}$", message = "Teléfono inválido")
    private String telefono;

    @Size(max = 100)
    private String ocupacion;

    @NotNull(message = "El nivel educativo es obligatorio")
    private NivelEducativoTipo nivelEducativo;

    @NotNull(message = "El estado civil es obligatorio")
    private EstadoCivilTipo estadoCivil;

    @Size(max = 20, message = "El sexo es demasiado largo")
    @Pattern(
        regexp = "^(Masculino|Femenino|Otro)$",
        message = "Sexo inválido. Valores permitidos: Masculino, Femenino, Otro"
    )
    private String sexo;

    @Email(message = "Email inválido")
    @Size(max = 150, message = "El email es demasiado largo")
    private String email;

    @NotEmpty(message = "Debe informar al menos un conviviente")
    private List<@NotNull(message = "El conviviente no puede ser nulo") ConvivienteTipo> convivientes;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;
}
