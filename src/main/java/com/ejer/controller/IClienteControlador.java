package com.ejer.controller;

import com.ejer.hibernate.entity.Cliente;

import java.util.List;

/**
 *
 */
public interface IClienteControlador<T> extends IControlador<T> {
    List getListaElementosOrdenadorNumeroIdentificacion();

    Cliente findCliente(final String numeroIdentificacion);
}
