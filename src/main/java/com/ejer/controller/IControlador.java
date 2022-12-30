package com.ejer.controller;

import java.util.List;

/**
 * Interfaz para implementación de métodos relacionados con las clases Controlador para las entidades Cliente
 * Implementa IService
 * @param <T> la clase entidad
 * @author Luis Cueto
 */
public interface IControlador<T> {
    List getListaElementos();
    void insertarElemento(T cliente);
    void eliminarElemento(String numeroIdentificacion);
    void updateElemento(final T cliente);
}
