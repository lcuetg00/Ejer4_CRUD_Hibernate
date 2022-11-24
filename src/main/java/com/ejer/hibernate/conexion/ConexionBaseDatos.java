package com.ejer.hibernate.conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase que sigue un patrón Singleton
 * Utilizada para la conexión con la base de datos
 * Contiene un EntityManagerFactory para la conexión
 */
public class ConexionBaseDatos {
    /**
     * Instancia de la propia clase
     */
    private static ConexionBaseDatos instanciaConexion;
    /**
     * Para la conexión con la base de datos. Utiliza 'unidadPersistencia' como unidad de permanencia
     */
    private EntityManagerFactory entityManagerFactory;
    /**
     * Contiene el nombre de la unidad de permanencia que utilizará la base de datos, almacenado en persistence.xml
     * Valor por defecto: 'persistencia'
     */
    private String unidadPersistencia = "persistencia";

    /**
     * Constructor privado
     * Creado para prevenir que desde fuera se creen instancias de esta clase (solo existirá 1 instancia, siguiendo el patrón Singleton)
     */
    private ConexionBaseDatos() {
    }

    /**
     * Crea una conexión con la base de datos a través del EntityManagerFactory, utilizando la variable unidadPersistencia de esta clase
     */
    public void crearConexion() {
        if(unidadPersistencia == null) {
            throw new NullPointerException("Clase ConexionBaseDatos: unidadPersistencia es null");
        }
        this.entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia);
    }

    /**
     * Devuelve la instancia de esta clase, si no existe la crea
     * @return
     */
    public static ConexionBaseDatos getInstance() {
        if(instanciaConexion == null) {
            instanciaConexion = new ConexionBaseDatos();
        }
        return instanciaConexion;
    }

    /**
     * Devuelve un EntityManager creado utilizando el EntityManagerFactory de esta clase
     * @return
     */
    public EntityManager getEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }

    /**
     * Cierra el el EntityManagerFactory de esta clase para cerrar la conexión con la base de datos
     */
    public void cerrarConexion() {
        this.entityManagerFactory.close();
    }

    public void setUnidadPersistencia(String unidadPersistencia) {
        this.unidadPersistencia = unidadPersistencia;
    }

}
