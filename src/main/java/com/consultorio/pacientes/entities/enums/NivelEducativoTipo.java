package com.consultorio.pacientes.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NivelEducativoTipo {
    SIN_ESCOLARIDAD("sin_escolaridad"),
    PRIMARIO("primario"),
    SECUNDARIO("secundario"),
    TERCIARIO("terciario"),
    UNIVERSITARIO("universitario"),
    OTRO("otro");

    private final String value;

    NivelEducativoTipo(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static NivelEducativoTipo fromValue(String raw) {
        if (raw == null) {
            return null;
        }
        for (NivelEducativoTipo tipo : values()) {
            if (tipo.value.equalsIgnoreCase(raw)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Nivel educativo inválido: " + raw);
    }
}
