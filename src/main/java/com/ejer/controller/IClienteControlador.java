package com.ejer.controller;

import com.ejer.hibernate.entity.Cliente;

import java.util.List;

public interface IClienteControlador {
    List getListaElementos();
    List getListaElementosOrdenadorNumeroIdentificacion();
    void insertarCliente(Cliente cliente);
    void eliminarCliente(String numeroIdentificacion);
    void updateCliente(final Cliente cliente);
    Cliente findCliente(final String numeroIdentificacion);
}
