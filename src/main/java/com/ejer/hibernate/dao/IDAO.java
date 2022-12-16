package com.ejer.hibernate.dao;

import java.util.List;

/**
 * Interfaz con métodos básicos para acceder a la base de datos
 * @param <T>
 * @author Luis Cueto
 */
public interface IDAO<T> {

    /**
     * Devuelve una lista con todos los elementos de la tabla
     * @return List con elementos de tipo T
     */
    List<T> getListaElementos();
    /**
     * Inserta el objeto 'elemento' en la base de datos
     * @param elemento
     */
    void insertarElemento(T elemento);
    /**
     * Elimina el objeto 'elemento' en la base de datos
     * @param elemento
     */
    void eliminarElemento(T elemento);
}
