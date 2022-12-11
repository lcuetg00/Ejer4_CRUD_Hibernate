package com.ejer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ejer.controller.ClienteControlador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class TestClientes {


    @BeforeEach
    void setUp() {
        ClienteControlador conCli = new ClienteControlador();

    }

    @Test
    @DisplayName("Test de validación de NIE")
    void testValidarNIE() {
        String nie = "12312312A";
        ClienteControlador.validarNumeroDocumentacion(nie);

    }

    @Test
    @DisplayName("Test de validación de NIF")
    void testValidarNIF() {
        String nif = "";
        ClienteControlador.validarNumeroDocumentacion(nif);

    }

    @Test
    @DisplayName("Test de validación de CIF")
    void testValidarCIF() {
        String cif = "";
        ClienteControlador.validarNumeroDocumentacion(cif);
    }
}
