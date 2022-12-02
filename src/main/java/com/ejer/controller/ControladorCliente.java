package com.ejer.controller;

import com.ejer.service.ServiceCliente;

import java.security.InvalidParameterException;

public class ControladorCliente {

    /**
     * Patrón que siguen los NIE: números más una letra final
     */
    private static final String REGEX_NIE = "[0-9]++[A-Z]";
    /**
     * Patrón que siguen los NIF: Letra X o T seguido de número y una letra final
     */
    private static final String REGEX_NIF = "[XT][0-9]++[A-Z]";
    /**
     * Patrón que siguen los CIF: Letra seguida de números y finalmente puede acabar en una letra o en un número
     */
    private static final String REGEX_CIF = "[A-Z][0-9]++[A-Z_0-9]";

    /**
     * Comprueba si un número de identificación es válido
     * @param numeroIdentificacion
     * @return
     */
    public static boolean validarNumeroDocumentacion(final String numeroIdentificacion) {
        if(numeroIdentificacion == null) {
            throw new InvalidParameterException();
        }
        boolean esValido = false;

        if(numeroIdentificacion.matches(REGEX_NIE)) {
            esValido = ServiceCliente.validarNie(numeroIdentificacion);
        }
        if(numeroIdentificacion.matches(REGEX_NIF)) {
            esValido = ServiceCliente.validarNif(numeroIdentificacion);
        }
        if(numeroIdentificacion.matches(REGEX_CIF)) {
            esValido = ServiceCliente.valifarCif(numeroIdentificacion);
        }

        return esValido;
    }
}
