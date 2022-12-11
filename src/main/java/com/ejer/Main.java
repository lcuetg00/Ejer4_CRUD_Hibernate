package com.ejer;

import com.ejer.hibernate.conexion.ConexionBaseDatos;
import org.hibernate.exception.SQLGrammarException;

public class Main {

    static {
        System.setProperty("Log4jContextSelector",
                "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector");
    }

    public static void main( String[] args ) {
        try {

            ConexionBaseDatos.getInstance().crearConexion();
        } catch(SQLGrammarException e) {

        }
        //No se ha podido abrir conexion

        //Consola consola = new Consola();
        ConexionBaseDatos.getInstance().cerrarConexion();
    }
}
