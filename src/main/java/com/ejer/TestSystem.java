package com.ejer;

import com.ejer.hibernate.conexion.ConexionBaseDatos;
import com.ejer.hibernate.entity.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestSystem {


    public static void main(String[] args) {
    }

    public static void insertarCliente(Cliente cliente) {
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
}
