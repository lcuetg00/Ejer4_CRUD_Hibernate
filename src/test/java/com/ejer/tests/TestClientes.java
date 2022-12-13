package com.ejer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ejer.controller.ClienteControlador;
import com.ejer.service.ClienteService;
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
        String nie = "84487226S";
        assertTrue(ClienteService.validarNumeroDocumentacion(nie));

    }

    @Test
    @DisplayName("Test de validación de NIF")
    void testValidarNIF() {
        String nif = "Z7195000F";
        ClienteService.validarNumeroDocumentacion(nif);

    }

    @Test
    @DisplayName("Test de validación de CIF")
    void testValidarCIF() {
        String cif = "";
        ClienteService.validarNumeroDocumentacion(cif);
    }
}
