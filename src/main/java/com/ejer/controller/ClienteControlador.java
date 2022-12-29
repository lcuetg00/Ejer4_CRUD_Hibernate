package com.ejer.controller;

import com.ejer.hibernate.entity.Cliente;
import com.ejer.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * Clase ClienteControlador
 * Implementa métodos para comunicarse con ClienteService
 * @author Luis Cueto
 */
public class ClienteControlador implements IClienteControlador {

    /**
     * Constructor que inicializa una isntancia de ClienteService
     */
    public ClienteControlador() {
        clienteService = new ClienteService();
    }

    /**
     * Instancia de ServiceCliente
     */
    private ClienteService clienteService;

    /**
     * Logger para la clase ClienteControlador
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(ClienteControlador.class);

    public boolean validarNumeroDocumentacion(final String numeroIdentificacion) {
        return clienteService.validarNumeroDocumentacion(numeroIdentificacion);
    }

    /**
     * Llama a ClienteService para recoger una lista con todos las entidades de tipo Cliente en la base de datos
     * {@link ClienteService#getListaElementos()}
     * @return
     */
    public List getListaElementos() {
        return clienteService.getListaElementos();
    }

    /**
     * Llama a ClienteService para recoger una lista con todos las entidades de tipo Cliente en la base de datos
     * {@link ClienteService#getListaElementosOrdenadorNumeroIdentificacion()}
     * @return
     */
    public List getListaElementosOrdenadorNumeroIdentificacion() {
        return clienteService.getListaElementosOrdenadorNumeroIdentificacion();
    }

    /**
     * Llama a ClienteService para insertar un objeto de tipo Cliente
     * {@link ClienteService#insertarElemento(Cliente)}
     * @throws InvalidParameterException si el cliente argumentado es null
     * @param cliente
     */
    public void insertarCliente(final Cliente cliente) {
        if(cliente == null) {
            throw new InvalidParameterException();
        }
        clienteService.insertarElemento(cliente);
    }

    /**
     * Llama a ClienteService para eliminar Cliente por su número de identificación
     * {@link ClienteService#eliminarCliente(String)}
     * @param numeroIdentificacion
     */
    public void eliminarCliente(final String numeroIdentificacion) {
        if(numeroIdentificacion == null) {
            throw new InvalidParameterException();
        }
        clienteService.eliminarCliente(numeroIdentificacion);
    }

    /**
     * Llama a ClienteService para recoger un Cliente que tenga el mismo número de identificación
     * {@link ClienteService#findCliente(String)}
     * @param numeroIdentificacion
     * @throws InvalidParameterException si numeroIdentificacion es null
     */
    public Cliente findCliente(final String numeroIdentificacion) {
        if(numeroIdentificacion == null) {
            throw new InvalidParameterException();
        }
        return clienteService.findCliente(numeroIdentificacion);
    }

    /**
     * Llama a ClienteService para recoger un Cliente que tenga el mismo número de identificación
     * {@link ClienteService#findCliente(String)}
     * @param cliente
     * @throws InvalidParameterException si el cliente argumentado es null
     */
    public void updateCliente(final Cliente cliente) {
        if(cliente == null) {
            throw new InvalidParameterException();
        }
        clienteService.updateCliente(cliente);
    }
}
