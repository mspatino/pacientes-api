package com.consultorio.pacientes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "historia_clinica")
@Getter
@Setter
@NoArgsConstructor
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Una historia clínica pertenece a un solo paciente
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Column(nullable = false)
    private LocalDateTime fechaAlta;

    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diagnostico> diagnosticos = new ArrayList<>();

    @Column(length = 4000)
    private String motivoConsulta;

    @Column(length = 5000)
    private String observaciones;

    @Column(nullable = false)
    private boolean activa = true;

    @PrePersist
    public void prePersist() {
    this.fechaAlta = LocalDateTime.now();
    }

    public void addDiagnostico(Diagnostico diagnostico) {
        diagnosticos.add(diagnostico);
        diagnostico.setHistoriaClinica(this);
    }

    public void removeDiagnostico(Diagnostico diagnostico) {
        diagnosticos.remove(diagnostico);
        diagnostico.setHistoriaClinica(null);
    }

}

