package com.boticasaludica.model;

import java.math.BigDecimal;

public class Producto {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private boolean activo;

    public Producto() {
    }

    public Producto(Long id, String nombre, BigDecimal precio, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
