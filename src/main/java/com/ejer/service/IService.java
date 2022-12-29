package com.ejer.service;

import com.ejer.hibernate.entity.Cliente;

import java.util.List;

/**
 * Interfaz para
 * @author Luis Cueto
 */
public interface IService<T> {

    /**
     * Devuelve una lista con todos los elementos de la tabla
     * @return List con elementos de tipo T
     */
    List getListaElementos();

    /**
     * Inserta el objeto 'elemento' en la base de datos
     * @param elemento
     */
    void insertarElemento(T elemento);

    void eliminarCliente(final String numIdentificacion);

    void insertarElemento(final Cliente cliente);

    Cliente findCliente(final String numIdentificacion);

    void updateCliente(final Cliente cliente);

    /**
     * Devuelve si el número de identificación es válido o no lo es
     * @param numeroIdentificacion
     * @return
     */
    boolean validarNumeroDocumentacion(final String numeroIdentificacion);

    /**
     * Recoge una lista de elementos ordenadas por su número de identificación
     * @return
     */
    List getListaElementosOrdenadorNumeroIdentificacion();

}
