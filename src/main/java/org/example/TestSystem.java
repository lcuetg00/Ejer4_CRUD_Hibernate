package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestSystem {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("persistencia");

    public static void main(String[] args) {
        System.out.println("aqui");
        insertarCliente("12312322B","Paco","Gonzalez");

        ENTITY_MANAGER_FACTORY.close();
    }

    public static void insertarCliente(String dni, String nombre, String primerApellido) {
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try {
            et = em.getTransaction();
            et.begin();
            Cliente cliente = new Cliente();
            cliente.setDniCliente(dni);
            cliente.setNombreCliente(nombre);
            cliente.setPrimerApellidoCliente(primerApellido);
            em.persist(cliente);
            et.commit();


        } catch (Exception e) {
            if(et != null) {
                et.rollback();
            }
            e.printStackTrace();
        }
        finally {
            em.close();
        }
    }
}
