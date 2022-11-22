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
     */
    public void insertarElemento(Cliente cliente) {
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
            //e.printStackTrace();
        }
        finally {
            entityManager.close();
        }
    }

    /**
     * Elimina un elemento de tipo 'Cliente' en la tabla 'cliente'
     * @param cliente
     */
    public void eliminarElemento(Cliente cliente) {

    }
}
