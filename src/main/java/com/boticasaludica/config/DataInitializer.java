package com.boticasaludica.config;

import com.boticasaludica.model.Producto;
import com.boticasaludica.model.Role;
import com.boticasaludica.model.Usuario;
import com.boticasaludica.store.MemoriaStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MemoriaStore memoriaStore;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(MemoriaStore memoriaStore, PasswordEncoder passwordEncoder) {
        this.memoriaStore = memoriaStore;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        cargarUsuarios();
        cargarProductos();
    }

    private void cargarUsuarios() {
        Usuario admin = new Usuario(
            1L,
            "admin",
            passwordEncoder.encode("admin123"),
            Role.ADMIN,
            true
        );

        Usuario cajero = new Usuario(
            2L,
            "cajero",
            passwordEncoder.encode("cajero123"),
            Role.CAJERO,
            true
        );

        memoriaStore.getUsuarios().put(admin.getUsername(), admin);
        memoriaStore.getUsuarios().put(cajero.getUsername(), cajero);
    }

    private void cargarProductos() {
        memoriaStore.getProductos().put(
            1L,
            new Producto(1L, "Paracetamol 500mg", new BigDecimal("2.50"), true)
        );

        memoriaStore.getProductos().put(
            2L,
            new Producto(2L, "Ibuprofeno 400mg", new BigDecimal("4.00"), true)
        );

        memoriaStore.getProductos().put(
            3L,
            new Producto(3L, "Amoxicilina 500mg", new BigDecimal("8.50"), true)
        );

        memoriaStore.getProductos().put(
            4L,
            new Producto(4L, "Alcohol 70%", new BigDecimal("6.50"), true)
        );

        memoriaStore.getProductos().put(
            5L,
            new Producto(5L, "Mascarilla", new BigDecimal("1.00"), true)
        );
    }
}
