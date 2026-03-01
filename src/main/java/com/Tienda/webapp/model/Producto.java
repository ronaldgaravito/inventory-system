package com.Tienda.webapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @Column(name = "id_producto", length = 10)
    private String idProducto;

    // VARCHAR(50)
    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    // INT en SQL
    @Column(nullable = false)
    private int precio;

    // CHAR(1) en SQL
    @Column(name = "estado_producto", nullable = false, length = 1)
    private String estadoProducto;

    // Categoria del producto (frutas, lacteos, etc.)
    @Column(name = "categoria", length = 30)
    private String categoria;

    // URL de la imagen para el frontend
    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    // Stock del producto
    @Column(columnDefinition = "int default 0")
    private int stock = 0;

    // CONSTRUCTOR VACÍO (Obligatorio para Hibernate/JPA)
    public Producto() {
    }

    // CONSTRUCTOR CON PARÁMETROS (Para facilitar crear objetos)
    public Producto(String idProducto, String descripcion, int precio, String estadoProducto, int stock) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estadoProducto = estadoProducto;
        this.stock = stock;
    }

    // GETTERS Y SETTERS
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(String estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
