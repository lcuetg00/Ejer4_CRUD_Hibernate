package com.ejer.controller;

import com.ejer.hibernate.entity.Cliente;
import com.ejer.service.ServiceCliente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;

public class ControladorCliente {

    /**
     * Instancia de ServiceCliente
     */
    private ServiceCliente serCli = new ServiceCliente();

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
    static private final Logger logger  = LogManager.getLogger(ControladorCliente.class.getName());


    /**
     * Comprueba si un número de identificación es válido
     * @param numeroIdentificacion
     * @return
     */
    public static boolean validarNumeroDocumentacion(final String numeroIdentificacion) {
        logger.debug("Clase ControladorCliente Método validarNumeroDocumentacion(final String numeroIdentificacion): se va a comprobar {}",numeroIdentificacion);
        logger.info("Se va a comprobar el Número de Identificación");
        if(numeroIdentificacion == null) {
            logger.error("Clase ControladorCliente Método validarNumeroDocumentacion(final String numeroIdentificacion): el parámetro numeroIdentificacion es null");
            throw new InvalidParameterException();
        }
        boolean esValido = false;

        if(numeroIdentificacion.matches(REGEX_NIE)) {
            logger.debug("Clase ControladorCliente Método validarNumeroDocumentacion(final String numeroIdentificacion): {} concuerda con {}",numeroIdentificacion,REGEX_NIE);
            logger.info("El Número de Identificación es un NIE");
            esValido = ServiceCliente.validarNie(numeroIdentificacion);
        }
        if(numeroIdentificacion.matches(REGEX_NIF)) {
            logger.debug("Clase ControladorCliente Método validarNumeroDocumentacion(final String numeroIdentificacion): {} concuerda con {}",numeroIdentificacion,REGEX_NIF);
            logger.info("El Número de Identificación es un NIF");
            esValido = ServiceCliente.validarNif(numeroIdentificacion);
        }
        if(numeroIdentificacion.matches(REGEX_CIF)) {
            logger.debug("Clase ControladorCliente Método validarNumeroDocumentacion(final String numeroIdentificacion): {} concuerda con {}",numeroIdentificacion,REGEX_CIF);
            logger.info("El Número de Identificación es un CIF");
            esValido = ServiceCliente.valifarCif(numeroIdentificacion);
        }

        return esValido;
    }

    public void insertarCliente(final Cliente cliente) {
        logger.debug("Clase ControladorCliente Método insertarCliente(final Cliente cliente): se va a insertar {}",cliente.toString());
        logger.info("ControladorCliente: se va a insertar el cliente");
        if(this.validarNumeroDocumentacion(cliente.getDniCliente())) {
            logger.debug("Clase ControladorCliente Método insertarCliente(final Cliente cliente): Número de Indetificador del cliente es válido");
            logger.debug("Clase ControladorCliente Método insertarCliente(final Cliente cliente): Llamado a ServiceCliente para insertar el cliente");
            serCli.insertarCliente(cliente);
        } else {
            logger.debug("Clase ControladorCliente Método insertarCliente(final Cliente cliente): Número de Indetificador del cliente no es válido");

        }
    }
}
