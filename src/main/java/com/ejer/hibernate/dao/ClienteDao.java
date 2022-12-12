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
 */
public class ClienteDao implements IOperacionesDao<Cliente>{

    /**
     * Logger para la clase ClienteDao
     */
    static private final Logger LOGGER = LoggerFactory.getLogger(ClienteDao.class.getName());

    /**
     * Devuelve una lista con todos los elementos de la tabla 'cliente'
     * @return List con elementos de tipo Cliente
     */
    public List recogerListaElementos() {
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        String query = "SELECT c FROM Cliente c";
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(query, Cliente.class);
        try {
            return typedQuery.getResultList();

        } catch (Exception e) {

            throw e;
        }
        finally {
            entityManager.close();
        }
    }

    /**
     * Devuelve una lista con todos los elementos de la tabla 'cliente' que estarán ordenador por su DNI
     * @return List con elementos de tipo Cliente ordenada
     */
    public List recogerListaElementosOrdenadosDNI() {
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        String query = "SELECT c FROM Cliente c ORDER BY c.numIdentificacion ASC";
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(query, Cliente.class);
        try {
            return typedQuery.getResultList();

        } catch (Exception e) {

            throw e;
        }
        finally {
            entityManager.close();
        }
    }

    /**
     * Inserta un elemento de tipo 'Cliente' en la tabla 'cliente'
     * @param cliente
     * @throws IllegalArgumentException
     */
    public void insertarElemento(Cliente cliente) throws IllegalArgumentException {
        if(cliente == null) {
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
            if(transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        finally {
            entityManager.close();
        }
    }

    /**
     * Elimina un elemento de tipo 'Cliente' en la tabla 'cliente'
     * @param cliente
     */
    public void eliminarElemento(Cliente cliente) throws IllegalArgumentException {
        if(cliente == null) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.remove(cliente);

            transaction.commit();

        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
        finally {
            entityManager.close();
        }

        }

    public Cliente getCliente(String numIdentificacion) throws IllegalArgumentException, NoSuchElementException {
        if(numIdentificacion == null) {
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
        }
        finally {
            entityManager.close();
        }

        return cliente;
    }


//    public boolean comprobarExisteClienteBaseDeDatos(String numIdentificacion) throws IllegalArgumentException {
//        if(numIdentificacion == null) {
//            throw new IllegalArgumentException();
//        }
//        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
//        String query = "SELECT c FROM Cliente c WHERE c.numIdentificacion = :numIdentificacion";
//        TypedQuery<Cliente> typedQuery = entityManager.createQuery(query, Cliente.class);
//        typedQuery.setParameter("numIdentificacion", numIdentificacion);
//        List<Cliente> clientes;
//        try {
//            Cliente cliente = typedQuery.getSingleResult();
//            return true;
//
//        } catch (NoResultException e) {
//            //No existe ese cliente
//            return false;
//        }
//        finally {
//            entityManager.close();
//        }
//
//    }
}
