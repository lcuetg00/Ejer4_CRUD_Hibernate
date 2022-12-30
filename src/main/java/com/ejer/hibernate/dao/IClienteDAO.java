package com.ejer.hibernate.dao;

import java.util.List;

/**
 * Interfaz para implementación de métodos relacionados con las clases DAO para las entidades Cliente
 * Implementa IDAO
 * @param <T> la clase entidad
 * @param <UUID> el tipo del identificador que utiliza la clase
 * @author Luis Cueto
 */
public interface IClienteDAO<T, UUID> extends IDAO<T, UUID> {

    /**
     * Devuelve una lista de elementos ordenador por el número de indentificación del cliente
     * @return
     */
    List getListaElementosOrdenadosNumeroIdentificacion();

    /**
     * Devuelve un cliente de la base de datos con el que coincida el número de identificación argumentado
     * @return
     */
    T findCliente(String numeroIdentificacion);

}
