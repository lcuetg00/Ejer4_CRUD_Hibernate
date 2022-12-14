package com.ejer.hibernate.conexion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Clase que sigue un patrón Singleton
 * Utilizada para la conexión con la base de datos
 * @author Luis Cueto
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
     * Logger para la clase ConexionBaseDatos
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(ConexionBaseDatos.class.getName());

    /**
     * Constructor privado
     * Creado para prevenir que desde fuera se creen instancias de esta clase (solo existirá 1 instancia, siguiendo el patrón Singleton)
     */
    private ConexionBaseDatos() {
        LOGGER.debug("Creada instancia ConexionBaseDatos");
    }

    /**
     * Crea una conexión con la base de datos a través del EntityManagerFactory, utilizando la variable unidadPersistencia de esta clase
     */
    public void crearConexion() {
        if(unidadPersistencia == null) {
            LOGGER.error("Clase ConexionBaseDatos Método crearConexion(): unidadPersistencia es null");
            throw new NullPointerException("Clase ConexionBaseDatos: unidadPersistencia es null");
        }
        this.entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia);
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ConexionBaseDatos Método crearConexion(): creado EntityManagerFactory con el valor de unidadePersistencia {}",this.unidadPersistencia);
        }
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Abierta conexión con la base de datos");
        }
    }

    /**
     * Devuelve la instancia de esta clase, si no existe la crea
     * @return
     */
    public static ConexionBaseDatos getInstance() {
        if(instanciaConexion == null) {
            instanciaConexion = new ConexionBaseDatos();
        }
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ConexionBaseDatos Método getInstance(): se ha devuelto la instacia de esta clase");
        }
        return instanciaConexion;
    }

    /**
     * Devuelve un EntityManager creado utilizando el EntityManagerFactory de esta clase
     * @return
     */
    public EntityManager getEntityManager() {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ConexionBaseDatos Método getEntityManager(): se ha creado un EntityManager y se ha devuelto");
        }
        return this.entityManagerFactory.createEntityManager();
    }

    /**
     * Cierra el el EntityManagerFactory de esta clase para cerrar la conexión con la base de datos
     */
    public void cerrarConexion() {
        if(this.entityManagerFactory.isOpen()) {
            this.entityManagerFactory.close();
        }
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ConexionBaseDatos Método cerrarConexion(): se ha cerrado la conexión ");
        }
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Cerrada la conexión con la base de datos");
        }
    }


    public void setUnidadPersistencia(final String unidadPersistencia) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ConexionBaseDatos Método setUnidadPersistencia(String): se ha insertado la nueva unidadPersistencia: {}",unidadPersistencia);
        }
        this.unidadPersistencia = unidadPersistencia;
    }

}
