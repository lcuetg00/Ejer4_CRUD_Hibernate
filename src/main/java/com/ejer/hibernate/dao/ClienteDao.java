package com.ejer.hibernate.dao;

import com.ejer.hibernate.conexion.ConexionBaseDatos;
import com.ejer.hibernate.entity.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

/**
 * Clase que implementa las operaciones de la tabla 'clientes' de la base de datos
 */
public class ClienteDao implements IOperacionesDao<Cliente>{

    /**
     * Devuelve una lista con todos los elementos de la tabla 'cliente'
     * @return List con elementos de tipo Cliente
     */
    public List recogerListaElementos() {
        List<Cliente> listaClientes;
        return null;
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
     * @param cliente
     */
    public void eliminarElemento(Cliente cliente) throws IllegalArgumentException {
        if(cliente == null) {
            throw new IllegalArgumentException();
        }

    }

    public Cliente getCliente(String dni) throws IllegalArgumentException {
        if(dni == null) {
            throw new IllegalArgumentException();
        }
        EntityManager entityManager = ConexionBaseDatos.getInstance().getEntityManager();
        String query = "SELECT c FROM cliente WHERE c.dni = :dniCliente";
        // Player p = entityManager.find(Player.class, key);
        try {


        return null;

        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        }
        finally {
            entityManager.close();
        }
    }


    public boolean comprobarExisteClienteBaseDeDatos(String dni) throws IllegalArgumentException {
        if(dni == null) {
            throw new IllegalArgumentException();
        }
        return false;
    }
}
