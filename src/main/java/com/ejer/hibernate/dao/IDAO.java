package com.ejer.hibernate.dao;

import java.util.List;

/**
 * Interfaz con métodos básicos para acceder a la base de datos
 * @param <T>
 * @author Luis Cueto
 */
public interface IDAO<T, UUID> {

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
     * Encuentra una fila en la base de datos que tenga el uuid argumentado
     * @param uuid
     * @return
     */
    T find(UUID uuid);

    /**
     * Elimina una fila en la base de datos que corresponda con su uuid argumentado
     * @param uuid
     */
    void delete(UUID uuid);

    /**
     * Devuelve el número de filas de la base de datos
     * @return número de filas en la base de datos
     */
    int count();
}
