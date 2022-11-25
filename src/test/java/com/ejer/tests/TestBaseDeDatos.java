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

    private ConexionBaseDatos conexion;
    private ClienteDao cliDao;
    private LocalDateTime fechaAltaCliente1;
    private LocalDateTime fechaAltaCliente2;
    private LocalDateTime fechaAltaCliente3;
    private Cliente cliente1;
    private Cliente cliente2;
    private Cliente cliente3;
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
        dtf = DateTimeFormatter.ISO_DATE_TIME;
    }

    @Test
    @DisplayName("Test para probar a insertar clientes y recoger lista de ellos")
    void testBaseDeDatos1() {
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
    void testBaseDeDatos2() {
        cliDao = new ClienteDao();
        cliente1.setDniCliente("12312312X");
        cliDao.insertarElemento(cliente1);
        cliDao.insertarElemento(cliente2);
        cliDao.insertarElemento(cliente3);

        List<Cliente> lista = cliDao.recogerListaElementosOrdenadosDNI();
        //Tenemos 3 elementos en la base de datos:
        assertEquals(lista.size(),3);

//        assertEquals(lista.get(0).toString(), "Cliente: DNI: 12312312A | Nombre: Mario | Primer Apellido: Fernández | Segundo Apellido: Alexis | Fecha de Alta: "+this.fechaAltaCliente1.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
//        assertEquals(lista.get(1).toString(), "Cliente: DNI: 23456789A | Nombre: Antonio | Primer Apellido: García | Segundo Apellido: Null | Fecha de Alta: "+this.fechaAltaCliente2.truncatedTo(ChronoUnit.SECONDS).format(dtf).toString());
//        assertEquals(lista.get(2).toString(), "Cliente: DNI: 30945834Y | Nombre: Paco | Primer Apellido: Rodríguez | Segundo Apellido: Null | Fecha de Alta: Null");

        ConexionBaseDatos.getInstance().cerrarConexion();
    }


}
