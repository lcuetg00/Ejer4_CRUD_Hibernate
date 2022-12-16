package com.ejer.service;

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


}
