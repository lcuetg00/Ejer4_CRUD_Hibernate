package com.ejer.service;

import com.ejer.hibernate.dao.ClienteDao;
import com.ejer.hibernate.entity.Cliente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.security.InvalidParameterException;

/**
 *
 */
public class ServiceCliente {

    /**
     * Instancia de ClienteDao
     */
    private ClienteDao cliDao;

    /**
     * Logger para la clase ServiceCliente
     */
    static private final Logger logger  = LogManager.getLogger(ServiceCliente.class.getName());


    public ServiceCliente() {
        cliDao = new ClienteDao();
    }

    public static boolean validarNie(final String nie) {
        return false;
    }

    public static boolean validarNif(final String nif) {
        return false;
    }

    public static boolean valifarCif(final String cif) {
        return false;
    }
    
    public void eliminarCliente(final String numIdentificacion) {

    }

    public void insertarCliente(final Cliente cliente) {
        logger.debug("");
        cliDao.insertarElemento(cliente);
    }


}
