package com.Tienda.webapp.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "rep_legal")
public class RepLegal {

    @Id
    @Column(name = "id_rep_legal", length = 10)
    private String idRepLegal;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(name = "numero_telefono", nullable = false, length = 15)
    private String numeroTelefono;

    @Column(nullable = false, length = 30)
    private String correo;

    @Column(nullable = false, length = 50)
    private String direccion;

    @Column(name = "estado_rep_legal", nullable = false, length = 1)
    private String estadoRepLegal;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public RepLegal() {
    }

    // Getters and Setters
    public String getIdRepLegal() {
        return idRepLegal;
    }

    public void setIdRepLegal(String idRepLegal) {
        this.idRepLegal = idRepLegal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstadoRepLegal() {
        return estadoRepLegal;
    }

    public void setEstadoRepLegal(String estadoRepLegal) {
        this.estadoRepLegal = estadoRepLegal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
