package com.boticasaludica.store;

import com.boticasaludica.model.Producto;
import com.boticasaludica.model.Usuario;
import com.boticasaludica.model.Venta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MemoriaStore {

    private final Map<String, Usuario> usuarios = new ConcurrentHashMap<>();
    private final Map<Long, Producto> productos = new ConcurrentHashMap<>();
    private final List<Venta> ventas = new CopyOnWriteArrayList<>();

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public Map<Long, Producto> getProductos() {
        return productos;
    }

    public List<Venta> getVentas() {
        return ventas;
    }
}
