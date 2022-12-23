package com.ejer;

import com.ejer.hibernate.conexion.ConexionBaseDatos;
import com.ejer.vista.Consola;
import org.hibernate.exception.SQLGrammarException;

public class Main {

    public static void main( String[] args ) {
        try {
            ConexionBaseDatos.getInstance().crearConexion();
            Consola consola = new Consola();
            consola.iniciarConsola();
            ConexionBaseDatos.getInstance().cerrarConexion();
        } catch(SQLGrammarException e) {
            //Error en la base de datos
        }

    }
}
