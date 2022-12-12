package com.ejer.controller;

import com.ejer.hibernate.entity.Cliente;
import com.ejer.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;

public class ClienteControlador {

    /**
     * Instancia de ServiceCliente
     */
    private ClienteService serCli = new ClienteService();

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
     * Logger para la clase ControladorCliente
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(ClienteControlador.class.getName());


    /**
     * Comprueba si un número de identificación es válido
     * @param numeroIdentificacion
     * @return
     */
    public static boolean validarNumeroDocumentacion(final String numeroIdentificacion) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ControladorCliente Método validarNumeroDocumentacion(final String numeroIdentificacion): se va a comprobar {}", numeroIdentificacion);
        }
        LOGGER.info("Se va a comprobar el Número de Identificación");
        if(numeroIdentificacion == null) {
            LOGGER.error("Clase ControladorCliente Método validarNumeroDocumentacion(final String numeroIdentificacion): el parámetro numeroIdentificacion es null");
            throw new InvalidParameterException();
        }
        boolean esValido = false;

        if(numeroIdentificacion.matches(REGEX_NIE)) {
            esValido = ClienteService.validarNie(numeroIdentificacion);
        }
        if(numeroIdentificacion.matches(REGEX_NIF)) {
            esValido = ClienteService.validarNif(numeroIdentificacion);
        }
        if(numeroIdentificacion.matches(REGEX_CIF)) {
            esValido = ClienteService.valifarCif(numeroIdentificacion);
        }

        return esValido;
    }

    public void insertarCliente(final Cliente cliente) {
        //if(this.validarNumeroDocumentacion(cliente.getNumIdentificacion())) {
             serCli.insertarCliente(cliente);
        //} else {
        //    LOGGER.debug("Clase ControladorCliente Método insertarCliente(final Cliente cliente): Número de Indetificador del cliente no es válido");

        //}
    }

    public void eliminarCliente(final String numeroIdentificacion) {
        serCli.eliminarCliente(numeroIdentificacion);
    }
}
