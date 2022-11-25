package com.ejer;

import com.ejer.consola.Consola;
import com.ejer.hibernate.conexion.ConexionBaseDatos;
import com.ejer.hibernate.dao.ClienteDao;
import com.ejer.hibernate.entity.Cliente;
import org.hibernate.exception.ConstraintViolationException;

import java.time.LocalDateTime;

public class Main {
    public static void main( String[] args ) {
        //try {
            ConexionBaseDatos.getInstance().crearConexion();
        //} catch
        //No se ha podido abrir conexion

        //Consola consola = new Consola();
        ConexionBaseDatos.getInstance().cerrarConexion();
    }
}
