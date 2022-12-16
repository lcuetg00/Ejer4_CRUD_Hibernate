package com.ejer.service;

import com.ejer.hibernate.dao.ClienteDAO;
import com.ejer.hibernate.entity.Cliente;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Clase ClienteService
 * Se comunica con ClienteDao para realizar operaciones con la base de datos
 * @author Luis Cueto
 */
public class ClienteService implements IService<Cliente> {

    /**
     * Constructor que inicializa una isntancia de ClienteDao
     */
    public ClienteService() {
        clienteDao = new ClienteDAO();
    }

    /**
     * Instancia de ClienteDao
     */
    private ClienteDAO clienteDao;

    /**
     * Patrón que siguen los NIE: números más una letra final
     */
    private static final String REGEX_NIE = "[0-9]++[A-Z]";

    /**
     * Patrón que siguen los NIF: Letra X o T seguido de número y una letra final
     */
    private static final String REGEX_NIF = "[XYZ][0-9]++[A-Z]";

    /**
     * Patrón que siguen los CIF: Letra seguida de números y finalmente puede acabar en una letra o en un número
     */
    private static final String REGEX_CIF = "[A-Z][0-9]++[A-Z_0-9]";

    /**
     * Lista de letras que utilizan los NIE en cada posición
     */
    private static final String[] LISTA_LETRAS_NIE = {"T","R","W","A","G","M","Y","F","P","D","X","B","N","J","Z","S","Q","V","H","L","C","K","E"};

    /**
     * Logger para la clase ServiceCliente
     */
    static private final Logger LOGGER = LogManager.getLogger(ClienteService.class);

    /**
     * Comprueba si un número de identificación es válido
     * Dependiendo del tipo del número de identificación, llamará a distintos métodos para comprobarlo:
     * {@link ClienteService#validarNie(String)} si es un DNI
     * {@link ClienteService#validarNif(String)} si es un número de identificación extranjero
     * {@link ClienteService#validarCif(String)} si es una entidad
     * @param numeroIdentificacion
     * @return true si es válido
     *         false si no es válido
     */
    public static boolean validarNumeroDocumentacion(final String numeroIdentificacion) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ClienteControlador Método validarNumeroDocumentacion(final String numeroIdentificacion): se va a comprobar {}", numeroIdentificacion);
        }
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Se va a comprobar el tipo de Número de Identificación");
        }
        if(numeroIdentificacion == null) {
            if(LOGGER.isErrorEnabled()) {
                LOGGER.error("Clase ClienteControlador Método validarNumeroDocumentacion(final String numeroIdentificacion): el parámetro numeroIdentificacion es null");
            }
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
            esValido = ClienteService.validarCif(numeroIdentificacion);
        }

        return esValido;
    }

    /**
     * Comprueba si la letra del nie pasado por parámetro es válida en comparaión con su parte numérica
     * @param nie
     * @return true si la letra corresponde con la parte numérica
     *         false si no corresponden
     */
    private static boolean validarNie(final String nie) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ClienteService Método validarNie(final String): se va a comprobar el nie {}", nie);
        }
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("El número de identificación es un NIE, comprobando que sea correcto");
        }

        String letra = nie.substring(nie.length()-1).toUpperCase();
        int num = Integer.parseInt(nie.substring(0,nie.length()-1));
        if(letra.equals(LISTA_LETRAS_NIE[num%LISTA_LETRAS_NIE.length])) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Comprueba si el nif
     * @param nif
     * @return true si
     *         false si
     */
    private static boolean validarNif(final String nif) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ClienteService Método validarNif(final String): se va a comprobar el nie {}", nif);
        }
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("El número de identificación es un NIF, comprobando que sea correcto");
        }


        //TODO terminar validacion nif

        return true;
    }

    /**
     * Comprueba si el cif
     * @param cif
     * @return true si
     *         false si
     */
    private static boolean validarCif(final String cif) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ClienteService Método validarCif(final String): se va a comprobar el cif {}", cif);
        }
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("El número de identificación es un CIF, comprobando que sea correcto");
        }

        //TODO terminar validacion cif

        return true;
    }

    /**
     * Llama a ClienteDao para recoger una lista de todos los elementos Cliente de la base de datos
     * {@link ClienteDAO#getListaElementos()}
     * @return
     */
    public List getListaElementos() {
        return clienteDao.getListaElementos();
    }
    
    public void eliminarCliente(final String numIdentificacion) {
        //FIXME validar numero de identificacion
        //if(this.validarNumeroDocumentacion(cliente.getNumIdentificacion())) {
        Cliente clienteEliminar = clienteDao.findCliente(numIdentificacion);
        //validar que no hay un cliente con el mismo dni
//        } else {
//                LOGGER.error("El Número de Identificación de incorrecto");

//        }

        clienteDao.eliminarElemento(clienteEliminar);
    }

    public void insertarElemento(final Cliente cliente) {
        if(this.validarNumeroDocumentacion(cliente.getNumIdentificacion())) {
            clienteDao.insertarElemento(cliente);
            //FIXME recoger excepcion si hay un cliente con el mismo nombre?, si no lanza SQLIntegrityConstraintViolationException

        } else {
            LOGGER.error("El Número de Identificación de incorrecto");
        }

    }

    public Cliente findCliente(final String numIdentificacion) {
        return clienteDao.findCliente(numIdentificacion);
    }

}
