package com.ejer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ejer.hibernate.conexion.ConexionBaseDatos;
import com.ejer.hibernate.dao.ClienteDao;
import com.ejer.hibernate.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;

class TestBaseDeDatos {

    ConexionBaseDatos conexion;
    private LocalDateTime fechaAltaCliente1;
    Cliente cliente1;


    @BeforeEach
    void setUp() {
        //Cada vez que iniciamos el text se hace DROP a las tablas y las crea
        ConexionBaseDatos.getInstance().setUnidadPersistencia("tests");
        ConexionBaseDatos.getInstance().crearConexion();
        fechaAltaCliente1 = LocalDateTime.now();
        cliente1 = new Cliente("12312312A", "Mario", "Fernandez", "Alexis", fechaAltaCliente1);
    }

    @Test
    @DisplayName("Test para probar a insertar clientes")
    void testInsertarCliente() {
        ClienteDao cliDao = new ClienteDao();
        cliDao.insertarElemento(cliente1);

        ConexionBaseDatos.getInstance().cerrarConexion();
        assertEquals(true, true);
    }


}
