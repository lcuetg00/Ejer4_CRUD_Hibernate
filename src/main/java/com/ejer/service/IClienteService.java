package com.ejer.service;

import com.ejer.hibernate.entity.Cliente;

import java.util.List;

/**
 * Interfaz para implementación de métodos relacionados con las clases Service para las entidades Cliente
 * Implementa IService
 * @param <T> la clase entidad
 * @author Luis Cueto
 */
public interface IClienteService<T> extends IService<T> {

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
