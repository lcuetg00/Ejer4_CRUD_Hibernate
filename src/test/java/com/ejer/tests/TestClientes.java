package com.ejer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ejer.controller.ControladorCliente;
import com.ejer.hibernate.conexion.ConexionBaseDatos;
import com.ejer.hibernate.dao.ClienteDao;
import com.ejer.hibernate.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class TestClientes {


    @BeforeEach
    void setUp() {
        ControladorCliente conCli = new ControladorCliente();

    }

    @Test
    @DisplayName("Test de validación de NIE")
    void testValidarNIE() {
        String nie = "12312312A";
        ControladorCliente.validarNieNifCif(nie);

    }

    @Test
    @DisplayName("Test de validación de NIF")
    void testValidarNIF() {
        String nif = "";
        ControladorCliente.validarNieNifCif(nif);

    }

    @Test
    @DisplayName("Test de validación de CIF")
    void testValidarCIF() {
        String cif = "";
        ControladorCliente.validarNieNifCif(cif);
    }
}