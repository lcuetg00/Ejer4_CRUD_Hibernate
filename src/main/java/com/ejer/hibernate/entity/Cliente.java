package com.ejer.hibernate.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "clientes")
/**
 * Entidad Cliente
 * @author Luis Cueto
 */
public class Cliente implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * Constructor sin parámetros utilizado por Hibernate
     */
    public Cliente() {
    }

    public Cliente(String dni, String nombre, String primerApellido, String segundoApellido, LocalDateTime fechaAltaCliente) {
        this.numIdentificacion = dni;
        this.nombreCliente = nombre;
        this.primerApellidoCliente = primerApellido;
        this.segundoApellidoCliente = segundoApellido;
        this.fechaAltaCliente = fechaAltaCliente;
    }

    @Id
    @Column(name = "idCliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    @Column(name = "numIdentificacion",
            length = 9,
            unique = true,
            nullable = false)
    private String numIdentificacion;

    @Column(name = "nombreCliente",
            length = 45,
            nullable = false)
    private String nombreCliente;

    @Column(name = "primerApellidoCliente",
            length = 45, nullable = false)
    private String primerApellidoCliente;

    @Column(name = "segundoApellidoCliente",
            length = 45)
    private String segundoApellidoCliente;

    //datetime en la base de datos
    @Column(name = "fechaAltaCliente")
    private LocalDateTime fechaAltaCliente;

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getPrimerApellidoCliente() {
        return primerApellidoCliente;
    }

    public void setPrimerApellidoCliente(String primerApellidoCliente) {
        this.primerApellidoCliente = primerApellidoCliente;
    }

    public String getSegundoApellidoCliente() {
        return segundoApellidoCliente;
    }

    public void setSegundoApellidoCliente(String segundoApellidoCliente) {
        this.segundoApellidoCliente = segundoApellidoCliente;
    }

    public LocalDateTime getFechaAltaCliente() {
        return fechaAltaCliente;
    }

    public void setFechaAltaCliente(LocalDateTime fechaAltaCliente) {
        this.fechaAltaCliente = fechaAltaCliente;
    }

    public String getNumIdentificacion() {
        return numIdentificacion;
    }

    public void setNumIdentificacion(String numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    public String toString() {
        StringBuilder caracteristicas = new StringBuilder();
        caracteristicas.append("Cliente: ");
        caracteristicas.append("Número de Identificacion: " + this.numIdentificacion + " | ");
        caracteristicas.append("Nombre: " + this.nombreCliente + " | ");
        caracteristicas.append("Primer Apellido: " + this.primerApellidoCliente + " | ");
        caracteristicas.append("Segundo Apellido: " + ((this.segundoApellidoCliente) != null ? this.segundoApellidoCliente : "Null") + " | ");
        caracteristicas.append("Fecha de Alta: " + ((this.fechaAltaCliente) != null ? this.fechaAltaCliente : "Null"));
        return caracteristicas.toString();
    }

}
