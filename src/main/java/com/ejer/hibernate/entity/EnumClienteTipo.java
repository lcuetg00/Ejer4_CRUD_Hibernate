package com.ejer.hibernate.entity;

public enum EnumClienteTipo {
    REGISTRADO,
    SOCIO;

    public String getStringTipoCliente() {
        String stringCliente;
        switch (name()) {
            case "REGISTRADO":
                stringCliente = "Registrado";
                break;
            case "SOCIO":
                stringCliente = "Socio";
                break;
            default:
                stringCliente = "No definido";
                break;
        }
        return stringCliente;
    }
}
