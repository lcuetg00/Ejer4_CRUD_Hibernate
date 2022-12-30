package com.ejer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ejer.controller.ClienteControlador;
import com.ejer.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class TestClientes {
    ClienteControlador conCli;

    @BeforeEach
    void setUp() {
         conCli = new ClienteControlador();

    }

    @Test
    @DisplayName("Test de validación de NIF")
    void testValidarNIF() {
        String nif = "84487226S";
        assertTrue(conCli.validarNumeroDocumentacion(nif));

        String nifCorto = "2250367R";
        assertTrue(conCli.validarNumeroDocumentacion(nifCorto));

        String nifMinuscula = "66325628e";
        assertTrue(conCli.validarNumeroDocumentacion(nifMinuscula));

    }

    @Test
    @DisplayName("Test de validación de NIE")
    void testValidarNIE() {
        String nie = "Z7195000F";
        assertTrue(conCli.validarNumeroDocumentacion(nie));

    }

    @Test
    @DisplayName("Test de validación de CIF")
    void testValidarCIF() {
        String cif = "F08255259";
        assertTrue(conCli.validarNumeroDocumentacion(cif));
    }
}
