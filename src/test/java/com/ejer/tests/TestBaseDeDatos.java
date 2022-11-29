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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

class TestBaseDeDatos {

    private ClienteDao cliDao;
    private LocalDateTime fechaAltaCliente1;
    private LocalDateTime fechaAltaCliente2;
    private Cliente cliente1;
    private Cliente cliente2;
    private Cliente cliente3;
    private Cliente cliente4;
    private Cliente cliente5;
    private DateTimeFormatter dtf;

    @BeforeEach
    void setUp() {
        //Cada vez que iniciamos el text se hace DROP a las tablas y las crea
        ConexionBaseDatos.getInstance().setUnidadPersistencia("tests");
        ConexionBaseDatos.getInstance().crearConexion();
        fechaAltaCliente1 = LocalDateTime.now().withNano(0);
        cliente1 = new Cliente("12312312A", "Mario", "Fernández", "Alexis", fechaAltaCliente1);
        fechaAltaCliente2 = LocalDateTime.now().withNano(0);
        cliente2 = new Cliente("23456789A", "Antonio", "García", null, fechaAltaCliente2);
        cliente3 = new Cliente("30945834Y", "Paco", "Rodríguez", null, null);
        cliente4 = new Cliente("23453567A", "Laura", "Lour", null, null);
        cliente5 = new Cliente("98234123H", "Álvaro", "Marqués", null, null);
        dtf = DateTimeFormatter.ISO_DATE_TIME;
    }

    @Test
    @DisplayName("Test para probar a insertar clientes y recoger lista de ellos")
    void testInsertarCliente() {
        cliDao = new ClienteDao();

        List<Cliente> listavacia = cliDao.recogerListaElementos();
        //Tenemos 0 elementos en la base de datos:
        assertEquals(listavacia.size(),0);

        cliDao.insertarElemento(cliente1);
        cliDao.insertarElemento(cliente2);
        cliDao.insertarElemento(cliente3);

        List<Cliente> lista = cliDao.recogerListaElementos();
        //Tenemos 3 elementos en la base de datos ahora:
        assertEquals(lista.size(),3);

        assertEquals(lista.get(0).toString(), "Cliente: DNI: 12312312A | Nombre: Mario | Primer Apellido: Fernández | Segundo Apellido: Alexis | Fecha de Alta: "+this.fechaAltaCliente1.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
        assertEquals(lista.get(1).toString(), "Cliente: DNI: 23456789A | Nombre: Antonio | Primer Apellido: García | Segundo Apellido: Null | Fecha de Alta: "+this.fechaAltaCliente2.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
        assertEquals(lista.get(2).toString(), "Cliente: DNI: 30945834Y | Nombre: Paco | Primer Apellido: Rodríguez | Segundo Apellido: Null | Fecha de Alta: Null");

        ConexionBaseDatos.getInstance().cerrarConexion();
    }

    @Test
    @DisplayName("Test para probar recoger una lista ordenada")
    void testRecogerListaOrdenadaClientes() {
        cliDao = new ClienteDao();
        cliente1.setDniCliente("10000000A");
        cliente2.setDniCliente("10000001A");
        cliente3.setDniCliente("10000002A");
        cliente4.setDniCliente("12000000Y");
        cliente5.setDniCliente("10500000K");
        cliDao.insertarElemento(cliente1);
        cliDao.insertarElemento(cliente3);
        cliDao.insertarElemento(cliente5);
        cliDao.insertarElemento(cliente2);
        cliDao.insertarElemento(cliente4);

        List<Cliente> lista = cliDao.recogerListaElementosOrdenadosDNI();
        //Tenemos 3 elementos en la base de datos:
        assertEquals(lista.size(),5);

        assertEquals(lista.get(0).toString(), "Cliente: DNI: 10000000A | Nombre: Mario | Primer Apellido: Fernández | Segundo Apellido: Alexis | Fecha de Alta: "+this.fechaAltaCliente1.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
        assertEquals(lista.get(1).toString(), "Cliente: DNI: 10000001A | Nombre: Antonio | Primer Apellido: García | Segundo Apellido: Null | Fecha de Alta: "+this.fechaAltaCliente2.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
        assertEquals(lista.get(2).toString(), "Cliente: DNI: 10000002A | Nombre: Paco | Primer Apellido: Rodríguez | Segundo Apellido: Null | Fecha de Alta: Null");
        assertEquals(lista.get(3).toString(), "Cliente: DNI: 10500000K | Nombre: Álvaro | Primer Apellido: Marqués | Segundo Apellido: Null | Fecha de Alta: Null");
        assertEquals(lista.get(4).toString(), "Cliente: DNI: 12000000Y | Nombre: Laura | Primer Apellido: Lour | Segundo Apellido: Null | Fecha de Alta: Null");

        ConexionBaseDatos.getInstance().cerrarConexion();
    }

    @Test
    @DisplayName("Test para probar a eliminar un cliente por su DNI")
    void testEliminarClienteConDni() {
        cliDao = new ClienteDao();

        cliDao.insertarElemento(cliente1);
        cliDao.insertarElemento(cliente2);
        cliDao.insertarElemento(cliente3);

        //cliDao.eliminarElemento(cliente2.getDniCliente());

        List<Cliente> lista = cliDao.recogerListaElementos();
        //Tenemos 2 elementos en la base de datos tras eliminar uno de ellos
        assertEquals(lista.size(),2);

        assertEquals(lista.get(0).toString(), "Cliente: DNI: 12312312A | Nombre: Mario | Primer Apellido: Fernández | Segundo Apellido: Alexis | Fecha de Alta: "+this.fechaAltaCliente1.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
        assertEquals(lista.get(2).toString(), "Cliente: DNI: 30945834Y | Nombre: Paco | Primer Apellido: Rodríguez | Segundo Apellido: Null | Fecha de Alta: Null");

        //cliDao.eliminarElemento(cliente3.getDniCliente());

        List<Cliente> lista2 = cliDao.recogerListaElementos();
        //Tenemos 2 elementos en la base de datos tras eliminar uno de ellos
        assertEquals(lista2.size(),1);

        assertEquals(lista.get(0).toString(), "Cliente: DNI: 12312312A | Nombre: Mario | Primer Apellido: Fernández | Segundo Apellido: Alexis | Fecha de Alta: "+this.fechaAltaCliente1.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
    }


}
