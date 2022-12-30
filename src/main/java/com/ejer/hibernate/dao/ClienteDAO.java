package com.ejer.hibernate.dao;

import com.ejer.hibernate.conexion.ConexionBaseDatos;
import com.ejer.hibernate.entity.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clase que implementa las operaciones de la tabla 'clientes' de la base de datos
 * @author Luis Cueto
 */
public class ClienteDAO implements IClienteDAO<Cliente, Integer> {

    /**
     * Logger para la clase ClienteDao
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(ClienteDAO.class);

    /**
     * Devuelve una lista con todos los elementos de la entidad Cliente en la base de datos
     * @return List con elementos de tipo Cliente
     */
    public List getListaElementos() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ClienteDao Método recogerListaElementos(): se va a recoger la lista de elementos");
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        String query = "SELECT c FROM Cliente c";
        try {
            TypedQuery<Cliente> typedQuery = entityManager.createQuery(query, Cliente.class);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Recogida lista de elementos Cliente");
            }
            return typedQuery.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al recoger la lista de elementos");
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Devuelve una lista con todos los elementos de la entidad Cliente en la base de datos
     * Esta lista estará ordenadora por el número de identificación de cada cliente
     * @return List con elementos de tipo Cliente ordenada
     */
    public List getListaElementosOrdenadosNumeroIdentificacion() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Clase ClienteDao Método getListaElementosOrdenadosNumeroIdentificacion(): se va a recoger la lista de elementos");
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        String query = "SELECT c FROM Cliente c ORDER BY c.numIdentificacion ASC";
        try {
            TypedQuery<Cliente> typedQuery = entityManager.createQuery(query, Cliente.class);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Recogida lista de elementos Cliente ordenada");
            }
            return typedQuery.getResultList();

        } catch (Exception e) {
            LOGGER.error("Error al recoger la lista de elementos Cliente ordenada");
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Inserta un elemento de tipo Cliente en la base de datos
     * @param cliente
     * @throws PersistenceException si ya existe el elemento en la base de datos
     * @throws IllegalArgumentException si el parámetro cliente es null
     */
    public void insertarElemento(final Cliente cliente) throws IllegalArgumentException {
        if (cliente == null) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(cliente);
            transaction.commit();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Insertado elemento: " + cliente.toString());
            }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Insertado Cliente correctamente");
            }
        } catch (Exception e) {
            if (transaction != null) {
                LOGGER.error("Clase ClienteDao Método insertarElemento(Cliente cliente): error al insertar cliente");
                transaction.rollback();
            }
            LOGGER.error("Error al insertar el elemento. Se ha hecho rollback a la transacción");
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Busca un cliente en la base de datos que tenga el parámetro uuid como clave primaria
     * @param uuid
     * @return
     * @throws NoSuchElementException si no encuentra el elemento en la base de datos
     * @throws IllegalArgumentException si el parámetro uuid es null
     */
    public Cliente find(final Integer uuid) throws NoSuchElementException, IllegalArgumentException{
        if (uuid == null) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Cliente cliente = entityManager.find(Cliente.class,uuid);
            if(cliente == null) {
                LOGGER.error("No se ha encontrado el cliente en la base de datos");
                throw new NoSuchElementException("No existe ele lemento en la base de datos");
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Encontrado cliente: " + cliente.toString());
            }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Encontrado cliente en la base de datos");
            }
            return cliente;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error al encontrar el elemento con valor {}. Se ha hecho rollback a la transacción",uuid);
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Elimina un cliente en la base de datos que tenga el parámetro uuid como clave primaria
     * @param uuid
     * @return
     * @throws NoSuchElementException si no encuentra el elemento en la base de datos al ir a eliminarlo
     * @throws IllegalArgumentException si el parámetro uuid es null
     */
    public void delete(final Integer uuid) throws NoSuchElementException, IllegalArgumentException {
        if (uuid == null) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Cliente cliente = this.find(uuid);
            if(cliente == null) {
                LOGGER.error("No se ha encontrado el cliente en la base de datos cuando se ha procedido a borrarlo");
                throw new NoSuchElementException("No existe el elemento en la base de datos");
            }
            entityManager.remove(entityManager.merge(cliente));
            transaction.commit();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Eliminado elemento: " + cliente.toString());
            }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Eliminado cliente en la base de datos");
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error al eliminar el elementocon uuid {}. Se ha hecho rollback a la transacción",uuid);
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public void updateElemento(final Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        EntityTransaction transaction = null;
        Cliente clienteBaseDatos = this.findCliente(cliente.getNumIdentificacion());

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            clienteBaseDatos.setNombreCliente(cliente.getNombreCliente());
            clienteBaseDatos.setPrimerApellidoCliente(cliente.getPrimerApellidoCliente());
            clienteBaseDatos.setSegundoApellidoCliente(cliente.getSegundoApellidoCliente());
            clienteBaseDatos.setNumIdentificacion(cliente.getNumIdentificacion());
            clienteBaseDatos.setFechaAltaCliente(cliente.getFechaAltaCliente());

            entityManager.merge(clienteBaseDatos);
            transaction.commit();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Actualizado elemento: {}",clienteBaseDatos.toString());
            }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Actualizado cliente en la base de datos");
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error al actualizar el elemento. Se ha hecho rollback a la transacción");
            throw e;
        } finally {
            entityManager.close();
        }

    }

    /**
     * Busca y devuelve un cliente de la base de datos que tenga el mismo número de identificación
     * @param numIdentificacion
     * @return cliente con el número de identificacion argumentado
     * @throws IllegalArgumentException
     * @throws NoSuchElementException
     */
    public Cliente findCliente(final String numIdentificacion) throws IllegalArgumentException, NoSuchElementException {
        if (numIdentificacion == null) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        String query = "SELECT c FROM Cliente c WHERE c.numIdentificacion = :numIdentificacion";
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(query, Cliente.class);
        typedQuery.setParameter("numIdentificacion", numIdentificacion);
        Cliente cliente = null;
        try {
            cliente = typedQuery.getSingleResult();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Recogido cliente utilizando su Número de Indentificación: {}",cliente.toString());
            }
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Recogido Cliente utilizando su Número de Identificación");
            }
        } catch (NoResultException e) {
            LOGGER.error("No se ha encontrado el cliente en la base de datos");
            throw e;
        } finally {
            entityManager.close();
        }

        return cliente;
    }
}
