package com.consultorio.pacientes.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EstadoCivilTipo {
    SOLTERO("soltero"),
    CASADO("casado"),
    UNION_CONVIVENCIAL("union_convivencial"),
    SEPARADO("separado"),
    DIVORCIADO("divorciado"),
    VIUDO("viudo"),
    OTRO("otro");

    private final String value;

    EstadoCivilTipo(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static EstadoCivilTipo fromValue(String raw) {
        if (raw == null) {
            return null;
        }
        for (EstadoCivilTipo tipo : values()) {
            if (tipo.value.equalsIgnoreCase(raw)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Estado civil inválido: " + raw);
    }
}
