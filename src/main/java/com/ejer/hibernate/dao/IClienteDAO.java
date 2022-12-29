package com.ejer.hibernate.dao;

import com.ejer.hibernate.entity.Cliente;

import java.util.List;

/**
 * Interfaz para
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
