package com.boticasaludica.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {

    private Long id;
    private String cajero;
    private LocalDateTime fecha;
    private List<DetalleVenta> detalles = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

    public Venta() {
    }

    public Venta(Long id, String cajero, LocalDateTime fecha,
                 List<DetalleVenta> detalles, BigDecimal total) {
        this.id = id;
        this.cajero = cajero;
        this.fecha = fecha;
        this.detalles = detalles;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
