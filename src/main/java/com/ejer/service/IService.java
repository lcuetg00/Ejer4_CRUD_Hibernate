package com.ejer.service;

import java.util.List;

/**
 * Interfaz con métodos básicos para las clases Service
 * @param <T>
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
    void insertarElemento(final T elemento);

    void eliminarElemento(final String numIdentificacion);

    T findElemento(final String numIdentificacion);

    void updateElemento(final T cliente);



}
