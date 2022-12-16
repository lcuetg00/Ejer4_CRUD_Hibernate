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
public class ClienteDAO implements IClienteDAO<Cliente> {

    /**
     * Logger para la clase ClienteDao
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(ClienteDAO.class);

    /**
     * Devuelve una lista con todos los elementos de la entidad Cliente en la base de datos
     *
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
            LOGGER.error("Clase ClienteDao Método recogerListaElementos(): error al recoger la lista de elementos");
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Devuelve una lista con todos los elementos de la entidad Cliente en la base de datos
     * Esta lista estará ordenadora por el número de identificación de cada cliente
     *
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
                LOGGER.info("Recogida lista de elementos Cliente");
            }
            return typedQuery.getResultList();

        } catch (Exception e) {
            LOGGER.error("Clase ClienteDao Método getListaElementosOrdenadosNumeroIdentificacion(): error al recoger la lista de elementos");
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Inserta un elemento de tipo Cliente en la base de datos
     * @param cliente
     * @throws PersistenceException si ya existe el elemento en la base de datos
     * @throws IllegalArgumentException
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error al insertar el elemento. Se ha hecho rollback a la transacción");
            throw e;
        } finally {
            entityManager.close();
        }
    }

    /**
     * Elimina un elemento de tipo Cliente en la base de datos
     *
     * @param cliente
     */
    public void eliminarElemento(final Cliente cliente) throws IllegalArgumentException {
        if (cliente == null) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            //entityManager.contains(cliente) ? cliente : entityManager.merge(cliente)
            entityManager.remove(entityManager.merge(cliente));

            transaction.commit();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Eliminado elemento: " + cliente.toString());
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOGGER.error("Error al eliminar el elemento. Se ha hecho rollback a la transacción");
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

            entityManager.merge(clienteBaseDatos);
            transaction.commit();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Actualizado elemento: ");
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
            //Solo puede haber un cliente con ese DNI concreto ya que en la base de datos
            //el campo DNI es unique


        } catch (NoResultException e) {
            LOGGER.error("No se ha encontrado el cliente en la base de datos");
            throw e;
        } finally {
            entityManager.close();
        }

        return cliente;
    }
}
