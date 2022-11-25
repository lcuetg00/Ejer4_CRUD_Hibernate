package com.ejer.hibernate.dao;

import com.ejer.hibernate.conexion.ConexionBaseDatos;
import com.ejer.hibernate.entity.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clase que implementa las operaciones de la tabla 'clientes' de la base de datos
 */
public class ClienteDao implements IOperacionesDao<Cliente>{

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

            e.printStackTrace();
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
        String query = "SELECT c FROM Cliente c ORDER BY c.dniCliente ASC";
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(query, Cliente.class);
        try {
            return typedQuery.getResultList();

        } catch (Exception e) {

            e.printStackTrace();
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

            //validar que no hay un cliente con el mismo dni
            //si lo hay, lanza SQLIntegrityConstraintViolationException

            entityManager.persist(cliente);

            transaction.commit();

        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        }
        finally {
            entityManager.close();
        }
    }

    /**
     * Elimina un elemento de tipo 'Cliente' en la tabla 'cliente'
     * @param dni
     */
    public void eliminarElemento(String dni) throws IllegalArgumentException {
        if(dni == null) {
            throw new IllegalArgumentException();
        }

    }

    public Cliente getCliente(String dni) throws IllegalArgumentException, NoSuchElementException {
        if(dni == null) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        String query = "SELECT c FROM Cliente WHERE c.dniCliente = :dni";
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(query, Cliente.class);
        List<Cliente> clientes;
        try {
            Cliente cliente = typedQuery.getSingleResult();
            //Solo puede haber un cliente con ese DNI concreto ya que en la base de datos
            //el campo DNI es unique


        } catch (NoSuchElementException e) {
            //lanzarla arriba la excepcion?
        }
        finally {
            entityManager.close();
        }

        return null;
    }


    public boolean comprobarExisteClienteBaseDeDatos(String dni) throws IllegalArgumentException {
        if(dni == null) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        String query = "SELECT c FROM Cliente WHERE c.dniCliente = :dni";
        TypedQuery<Cliente> typedQuery = entityManager.createQuery(query, Cliente.class);
        List<Cliente> clientes;
        try {
            Cliente cliente = typedQuery.getSingleResult();
            return true;

        } catch (NoSuchElementException e) {
            //No existe ese cliente
            return false;
        }
        finally {
            entityManager.close();
        }

    }
}
