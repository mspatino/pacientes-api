package com.consultorio.pacientes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;


@Entity
@Table(name = "historia_clinica")
@Getter
@Setter
@NoArgsConstructor
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "paciente_id", nullable = false)
    // Una historia clínica pertenece a un solo pacient
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Column(nullable = false)
    private LocalDateTime fechaAlta;

    // @OneToMany(cascade = CascadeType.ALL)
    // private List<Diagnostico> diagnosticos;

    @Column(length = 4000)
    private String motivoConsulta;

    @Column(length = 5000)
    private String observaciones;

    @Column(nullable = false)
    private Boolean activa = true;
}

