package com.consultorio.pacientes.specifications;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.consultorio.pacientes.entities.HistoriaClinica;

import jakarta.persistence.criteria.Join;

public class HistoriaClinicaSpecification {

        public static Specification<HistoriaClinica> dniPaciente(String dni) {
        return (root, query, cb) -> {
            if (dni == null) return null;

            Join<Object, Object> paciente = root.join("paciente");

            return cb.equal(paciente.get("dni"), dni);
        };
    }

    public static Specification<HistoriaClinica> nombrePaciente(String nombre) {
        return (root, query, cb) -> {
            if (nombre == null) return null;

            Join<Object, Object> paciente = root.join("paciente");

            return cb.like(
                    cb.lower(paciente.get("nombre")),
                    "%" + nombre.toLowerCase() + "%"
            );
        };
    }

    public static Specification<HistoriaClinica> apellidoPaciente(String apellido) {
        return (root, query, cb) -> {
            if (apellido == null) return null;

            Join<Object, Object> paciente = root.join("paciente");

            return cb.like(
                    cb.lower(paciente.get("apellido")),
                    "%" + apellido.toLowerCase() + "%"
            );
        };
    }

    public static Specification<HistoriaClinica> activa(Boolean activa) {
        return (root, query, cb) -> {
            if (activa == null) return null;

            return cb.equal(root.get("activa"), activa);
        };
    }

    public static Specification<HistoriaClinica> fechaDesde(LocalDate desde) {
        return (root, query, cb) -> {
            if (desde == null) return null;

            return cb.greaterThanOrEqualTo(root.get("fechaApertura"), desde);
        };
    }

    public static Specification<HistoriaClinica> fechaHasta(LocalDate hasta) {
        return (root, query, cb) -> {
            if (hasta == null) return null;

            return cb.lessThanOrEqualTo(root.get("fechaApertura"), hasta);
        };
    }

}
