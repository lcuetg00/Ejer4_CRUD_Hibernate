package com.ejer.hibernate.dao;

import com.ejer.hibernate.conexion.ConexionBaseDatos;
import com.ejer.hibernate.entity.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
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

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }

    }

    public void updateElemento(final Cliente cliente) {
        //Todo terminar método
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

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }

    }

    /**
     * @param numIdentificacion
     * @return
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
            //lanzarla arriba la excepcion?
        } finally {
            entityManager.close();
        }

        return cliente;
    }
}
