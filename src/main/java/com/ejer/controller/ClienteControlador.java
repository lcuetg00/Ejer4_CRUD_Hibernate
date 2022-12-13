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
 */
public class ClienteControlador {

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
    static private final Logger LOGGER = LoggerFactory.getLogger(ClienteControlador.class.getName());

    /**
     * Llama a ClienteService para recoger una lista con todos las entidades de tipo Cliente en la base de datos
     * {@link ClienteService#recogerListaElementos()}
     * @return
     */
    public List recogerListaElementos() {
        return clienteService.recogerListaElementos();
    }

    /**
     * Llama a ClienteService para insertar un objeto de tipo Cliente
     * {@link ClienteService#insertarCliente(Cliente)}
     * @param cliente
     */
    public void insertarCliente(final Cliente cliente) {
        clienteService.insertarCliente(cliente);
    }

    /**
     * Llama a ClienteService para eliminar Cliente por su número de identificación
     * {@link ClienteService#eliminarCliente(String)} 
     * @param numeroIdentificacion
     */
    public void eliminarCliente(final String numeroIdentificacion) {
        clienteService.eliminarCliente(numeroIdentificacion);
    }

    /**
     * Llama a ClienteService para recoger un Cliente que tenga el mismo número de identificación
     * {@link ClienteService#getCliente(String)}
     * @param numeroIdentificacion
     */
    public Cliente getCliente(final String numeroIdentificacion) {
        return clienteService.getCliente(numeroIdentificacion);
    }
}
