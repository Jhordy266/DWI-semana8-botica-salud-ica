package com.boticasaludica;

import com.boticasaludica.model.Role;
import com.boticasaludica.model.Usuario;
import com.boticasaludica.store.MemoriaStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BoticaSaludIcaApplicationTests {

    @Autowired
    private MemoriaStore memoriaStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void debePrecargarUsuariosYProductos() {
        assertThat(memoriaStore.getUsuarios()).hasSize(2);
        assertThat(memoriaStore.getProductos()).hasSize(5);

        Usuario admin = memoriaStore.getUsuarios().get("admin");
        Usuario cajero = memoriaStore.getUsuarios().get("cajero");

        assertThat(admin).isNotNull();
        assertThat(cajero).isNotNull();

        assertThat(admin.getRole()).isEqualTo(Role.ADMIN);
        assertThat(cajero.getRole()).isEqualTo(Role.CAJERO);
    }

    @Test
    void contrasenasDebenEstarCodificadasConBCrypt() {
        Usuario admin = memoriaStore.getUsuarios().get("admin");
        Usuario cajero = memoriaStore.getUsuarios().get("cajero");

        assertThat(admin.getPassword()).isNotEqualTo("admin123");
        assertThat(cajero.getPassword()).isNotEqualTo("cajero123");

        assertThat(passwordEncoder.matches("admin123", admin.getPassword())).isTrue();
        assertThat(passwordEncoder.matches("cajero123", cajero.getPassword())).isTrue();
    }
}
