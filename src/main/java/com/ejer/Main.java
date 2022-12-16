package com.ejer;

import com.ejer.hibernate.conexion.ConexionBaseDatos;
import org.hibernate.exception.SQLGrammarException;

public class Main {

    public static void main( String[] args ) {
        try {
            ConexionBaseDatos.getInstance().crearConexion();
            //Consola consola = new Consola();
            ConexionBaseDatos.getInstance().cerrarConexion();
        } catch(SQLGrammarException e) {
            //Error en la base de datos
        }

    }
}
