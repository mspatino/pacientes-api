package com.consultorio.pacientes.entities;

import java.time.LocalDateTime;

import com.consultorio.pacientes.entities.enums.EstadoTurno;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "turnos")
@Getter
@Setter
@NoArgsConstructor
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // Datos libres si no existe paciente
    private String nombreContacto;
    private String telefonoContacto;

    private LocalDateTime fechaHoraInicio;

    private Integer duracionMinutos; // ej: 45, 60

    @Enumerated(EnumType.STRING)
    private EstadoTurno estado;

    @Column(length = 1000)
    private String notas;

    public LocalDateTime getFechaHoraFin() {
        if (fechaHoraInicio == null || duracionMinutos == null) {
            return null;
        }
    return fechaHoraInicio.plusMinutes(duracionMinutos);
    }
}
