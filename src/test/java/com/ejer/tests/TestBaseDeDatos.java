package com.ejer.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ejer.controller.ClienteControlador;
import com.ejer.hibernate.conexion.ConexionBaseDatos;
import com.ejer.hibernate.dao.ClienteDAO;
import com.ejer.hibernate.entity.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

class TestBaseDeDatos {

    private ClienteDAO cliDao;
    private ClienteControlador clienteControlador;
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
        cliente1 = new Cliente("39029018L", "Mario", "Fernández", "Alexis", fechaAltaCliente1);
        fechaAltaCliente2 = LocalDateTime.now().withNano(0);
        cliente2 = new Cliente("07031947L", "Antonio", "García", null, fechaAltaCliente2);
        cliente3 = new Cliente("91906775V", "Paco", "Rodríguez", null, null);
        cliente4 = new Cliente("15982705M", "Laura", "Lour", null, null);
        cliente5 = new Cliente("48026219B", "Álvaro", "Marqués", null, null);
        dtf = DateTimeFormatter.ISO_DATE_TIME;
    }

    @Test
    @DisplayName("Test para insertar clientes y recoger lista de ellos")
    void testInsertarCliente() {
        clienteControlador = new ClienteControlador();

        List<Cliente> listavacia = clienteControlador.getListaElementos();
        //Tenemos 0 elementos en la base de datos:
        assertEquals(listavacia.size(),0);

        clienteControlador.insertarCliente(cliente1);
        clienteControlador.insertarCliente(cliente2);
        clienteControlador.insertarCliente(cliente3);

        List<Cliente> lista = clienteControlador.getListaElementos();
        //Tenemos 3 elementos en la base de datos ahora:
        assertEquals(lista.size(),3);

        assertEquals(lista.get(0).toString(), "Cliente: Número de Identificacion: 39029018L | Nombre: Mario | Primer Apellido: Fernández | Segundo Apellido: Alexis | Fecha de Alta: "+this.fechaAltaCliente1.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
        assertEquals(lista.get(1).toString(), "Cliente: Número de Identificacion: 07031947L | Nombre: Antonio | Primer Apellido: García | Segundo Apellido: Null | Fecha de Alta: "+this.fechaAltaCliente2.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
        assertEquals(lista.get(2).toString(), "Cliente: Número de Identificacion: 91906775V | Nombre: Paco | Primer Apellido: Rodríguez | Segundo Apellido: Null | Fecha de Alta: Null");

        ConexionBaseDatos.getInstance().cerrarConexion();
    }

    @Test
    @DisplayName("Test para recoger los datos de 1 cliente")
    void testGetCliente() {
        clienteControlador = new ClienteControlador();

        clienteControlador.insertarCliente(cliente1);
        clienteControlador.insertarCliente(cliente2);
        clienteControlador.insertarCliente(cliente3);

        assertEquals(clienteControlador.findCliente(cliente1.getNumIdentificacion()).toString(), "Cliente: Número de Identificacion: 39029018L | Nombre: Mario | Primer Apellido: Fernández | Segundo Apellido: Alexis | Fecha de Alta: "+this.fechaAltaCliente1.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
        assertEquals(clienteControlador.findCliente(cliente2.getNumIdentificacion()).toString(), "Cliente: Número de Identificacion: 07031947L | Nombre: Antonio | Primer Apellido: García | Segundo Apellido: Null | Fecha de Alta: "+this.fechaAltaCliente2.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
        assertEquals(clienteControlador.findCliente(cliente3.getNumIdentificacion()).toString(), "Cliente: Número de Identificacion: 91906775V | Nombre: Paco | Primer Apellido: Rodríguez | Segundo Apellido: Null | Fecha de Alta: Null");

        ConexionBaseDatos.getInstance().cerrarConexion();
    }

    @Test
    @DisplayName("Test para recoger una lista ordenada")
    void testRecogerListaOrdenadaClientes() {
        cliDao = new ClienteDAO();
        cliDao.insertarElemento(cliente1);
        cliDao.insertarElemento(cliente3);
        cliDao.insertarElemento(cliente5);
        cliDao.insertarElemento(cliente2);
        cliDao.insertarElemento(cliente4);

        List<Cliente> lista = cliDao.getListaElementosOrdenadosNumeroIdentificacion();
        //Tenemos 5 elementos en la base de datos:
        assertEquals(lista.size(),5);

        assertEquals(lista.get(0).toString(), cliente2.toString());
        assertEquals(lista.get(1).toString(), cliente4.toString());
        assertEquals(lista.get(2).toString(), cliente1.toString());
        assertEquals(lista.get(3).toString(), cliente5.toString());
        assertEquals(lista.get(4).toString(), cliente3.toString());

        ConexionBaseDatos.getInstance().cerrarConexion();
    }

    @Test
    @DisplayName("Test para probar a eliminar un cliente por su DNI")
    void testEliminarClienteConDni() {
        clienteControlador = new ClienteControlador();

        clienteControlador.insertarCliente(cliente1);
        clienteControlador.insertarCliente(cliente2);
        clienteControlador.insertarCliente(cliente3);

        clienteControlador.eliminarCliente(cliente2.getNumIdentificacion());
        //cliDao.eliminarElemento(cliente2.getDniCliente());

        List<Cliente> lista = clienteControlador.getListaElementos();
        //Tenemos 2 elementos en la base de datos tras eliminar uno de ellos
        assertEquals(lista.size(),2);

        assertEquals(lista.get(0).toString(), "Cliente: Número de Identificacion: 39029018L | Nombre: Mario | Primer Apellido: Fernández | Segundo Apellido: Alexis | Fecha de Alta: "+this.fechaAltaCliente1.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
        assertEquals(lista.get(1).toString(), "Cliente: Número de Identificacion: 91906775V | Nombre: Paco | Primer Apellido: Rodríguez | Segundo Apellido: Null | Fecha de Alta: Null");

        clienteControlador.eliminarCliente(cliente3.getNumIdentificacion());

        List<Cliente> lista2 = clienteControlador.getListaElementos();
        //Tenemos 2 elementos en la base de datos tras eliminar uno de ellos
        assertEquals(lista2.size(),1);

//        assertEquals(lista.get(0).toString(), "Cliente: DNI: 12312312A | Nombre: Mario | Primer Apellido: Fernández | Segundo Apellido: Alexis | Fecha de Alta: "+this.fechaAltaCliente1.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
    }


}
