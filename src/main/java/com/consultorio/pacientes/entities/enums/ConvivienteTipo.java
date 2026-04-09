package com.consultorio.pacientes.entities.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ConvivienteTipo {
    SOLO("solo"),
    PAREJA("pareja"),
    HIJOS("hijos"),
    HERMANOS("hermanos"),
    PADRES("padres"),
    OTROS_FAMILIARES("otros_familiares"),
    AMIGOS("amigos"),
    CUIDADOR("cuidador"),
    INSTITUCIONALIZADO("institucionalizado"),
    OTROS("otros");

    private final String value;

    ConvivienteTipo(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ConvivienteTipo fromValue(String raw) {
        if (raw == null) {
            return null;
        }
        for (ConvivienteTipo tipo : values()) {
            if (tipo.value.equalsIgnoreCase(raw)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valor de conviviente inválido: " + raw);
    }
}
