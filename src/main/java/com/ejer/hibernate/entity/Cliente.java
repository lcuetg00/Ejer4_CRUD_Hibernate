package com.ejer.hibernate.entity;

import com.ejer.vista.Consola;

import java.io.Serializable;
import java.math.BigDecimal;
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

    /**
     * Constructor para construir un cliente con todos sus atributos.
     * Si cuotaMaximaPago es null, se le asignará como tipo de cliente EnumClienteTipo.SOCIO
     * Si cuotaMaximaPago no es null, se le asignará como tipo de cliente EnumClienteTipo.REGISTRADO
     * @param dni
     * @param nombre
     * @param primerApellido
     * @param segundoApellido
     * @param fechaAltaCliente
     * @param cuotaMaximaPago
     */
    public Cliente(String dni, String nombre, String primerApellido, String segundoApellido, LocalDateTime fechaAltaCliente, BigDecimal cuotaMaximaPago) {
        this.numIdentificacion = dni;
        this.nombreCliente = nombre;
        this.primerApellidoCliente = primerApellido;
        this.segundoApellidoCliente = segundoApellido;
        this.fechaAltaCliente = fechaAltaCliente;
        this.cuotaMaximaPago = cuotaMaximaPago;

        if(cuotaMaximaPago == null) {
            this.enumCliente = EnumClienteTipo.SOCIO;
        } else {
            this.enumCliente = EnumClienteTipo.REGISTRADO;
        }
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

    @Column(name = "cuotaMaximaPago")
    private BigDecimal cuotaMaximaPago;

    @Column(name = "enumCliente",
            columnDefinition = "enum('SOCIO','REGISTRADO')")
    @Enumerated(EnumType.STRING)
    private EnumClienteTipo enumCliente;

    public int getIdCliente() {
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

    public BigDecimal getCuotaMaximaPago() {
        return cuotaMaximaPago;
    }

    public void setCuotaMaximaPago(BigDecimal cuotaMaximaPago) {
        if(cuotaMaximaPago != null) {
            this.cuotaMaximaPago = cuotaMaximaPago;
            this.setEnumCliente(EnumClienteTipo.REGISTRADO);
        }
    }

    public EnumClienteTipo getEnumCliente() {
        return enumCliente;
    }

    public void setEnumCliente(EnumClienteTipo enumCliente) {
        this.enumCliente = enumCliente;
    }

    /**
     * Devuelve un string con las características de un cliente con saltos de línea entre ellas
     * @return
     */
    public String getMetadatos() {
        StringBuilder caracteristicas = new StringBuilder();
        caracteristicas.append("Nombre Completo: " + this.nombreCliente + " " + this.primerApellidoCliente + " " +((this.segundoApellidoCliente) != null ? this.segundoApellidoCliente : "") + Consola.RETORNO_CARRO);
        caracteristicas.append("Número de Identificacion: " + this.numIdentificacion + Consola.RETORNO_CARRO);
        caracteristicas.append("Fecha de Alta: " + ((this.fechaAltaCliente) != null ? this.fechaAltaCliente : "No tiene"));
        if(this.enumCliente.equals(EnumClienteTipo.REGISTRADO)) {
            caracteristicas.append(Consola.RETORNO_CARRO);
            caracteristicas.append("Registrado con cuota Máxima: " + this.cuotaMaximaPago);
        }
        return caracteristicas.toString();
    }

    public String toString() {
        StringBuilder caracteristicas = new StringBuilder();
        caracteristicas.append("Cliente: " + this.enumCliente.getStringTipoCliente() + " | ");
        caracteristicas.append("Número de Identificacion: " + this.numIdentificacion + " | ");
        caracteristicas.append("Nombre: " + this.nombreCliente + " | ");
        caracteristicas.append("Primer Apellido: " + this.primerApellidoCliente + " | ");
        caracteristicas.append("Segundo Apellido: " + ((this.segundoApellidoCliente) != null ? this.segundoApellidoCliente : "Null") + " | ");
        caracteristicas.append("Fecha de Alta: " + ((this.fechaAltaCliente) != null ? this.fechaAltaCliente : "Null") + " | ");
        if(this.enumCliente.equals(EnumClienteTipo.REGISTRADO)) {
            caracteristicas.append("Cuota Máxima: " + this.cuotaMaximaPago);
        }
        return caracteristicas.toString();
    }

}
