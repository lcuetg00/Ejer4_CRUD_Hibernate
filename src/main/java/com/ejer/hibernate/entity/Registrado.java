package com.ejer.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "registrado")
/**
 * Entidad
 */
public class Registrado implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idCliente")
    private int idCliente;
    @Id
    @Column(name = "nombre",
            length = 45,
            nullable = false)
    private String nombre;
    @Column(name = "cuotaMaxima",
            nullable = false)
    private BigDecimal cuotaMaxima;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getCuotaMaxima() {
        return cuotaMaxima;
    }

    public void setCuotaMaxima(BigDecimal cuotaMaxima) {
        this.cuotaMaxima = cuotaMaxima;
    }
}
