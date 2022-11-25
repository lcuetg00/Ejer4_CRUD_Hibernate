package com.ejer.service;

import java.security.InvalidParameterException;

/**
 *
 */
public class ServiceCliente {

    /**
     * Patrón que siguen los NIE: números más una letra final
     */
    public static final String REGEX_NIE = "[0-9]++[A-Z]";
    /**
     * Patrón que siguen los NIF: Letra X o T seguido de número y una letra final
     */
    public static final String REGEX_NIF = "[XT][0-9]++[A-Z]";
    /**
     * Patrón ue siguen los CIF: Letra seguida de números y finalmente puede acabar en una letra o en un número
     */
    public static final String REGEX_CIF = "[A-Z][0-9]++[A-Z_0-9]";

    /**
     * Comprueba si un número de identificación es válido
     * @param numeroIdentificacion
     * @return
     */
    public boolean validarNieNifCif(final String numeroIdentificacion) {
        if(numeroIdentificacion == null) {
            throw new InvalidParameterException();
        }
        boolean esValido = false;

        switch (numeroIdentificacion) {
            case REGEX_NIE:
                esValido = this.validarNie(numeroIdentificacion);
                break;
            case REGEX_NIF:
                esValido = this.validarNif(numeroIdentificacion);
                break;
            case REGEX_CIF:
                esValido = this.valifarCif(numeroIdentificacion);
                break;
            default:
                break;
        }

        return esValido;
    }

    public boolean validarNie(final String nie) {
        return false;
    }

    public boolean validarNif(final String nif) {
        return false;
    }

    public boolean valifarCif(final String cif) {
        return false;
    }


}
