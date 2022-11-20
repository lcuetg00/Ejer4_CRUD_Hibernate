package org.example;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idCliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCliente;

    @Column(name = "dniCliente", nullable = false)
    private String dniCliente;

    @Column(name = "nombreCliente", nullable = false)
    private String nombreCliente;

    @Column(name = "primerApellidoCliente", nullable = false)
    private String primerApellidoCliente;

    @Column(name = "segundoApellidoCliente")
    private String segundoApellidoCliente;

    @Column(name = "fechaAltaCliente")
    private Date fechaAltaCliente;

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
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

    public Date getFechaAltaCliente() {
        return fechaAltaCliente;
    }

    public void setFechaAltaCliente(Date fechaAltaCliente) {
        this.fechaAltaCliente = fechaAltaCliente;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }
}
