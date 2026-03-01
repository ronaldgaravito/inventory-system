package com.Tienda.webapp.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @Column(name = "id_proveedor", length = 10)
    private String idProveedor;

    @Column(name = "nombre_proveedor", nullable = false, length = 50)
    private String nombreProveedor;

    @Column(name = "estado_proveedores", nullable = false, length = 1)
    private String estadoProveedores;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_rep_legal")
    private RepLegal repLegal;

    public Proveedor() {
    }

    // Getters and Setters
    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getEstadoProveedores() {
        return estadoProveedores;
    }

    public void setEstadoProveedores(String estadoProveedores) {
        this.estadoProveedores = estadoProveedores;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public RepLegal getRepLegal() {
        return repLegal;
    }

    public void setRepLegal(RepLegal repLegal) {
        this.repLegal = repLegal;
    }
}
