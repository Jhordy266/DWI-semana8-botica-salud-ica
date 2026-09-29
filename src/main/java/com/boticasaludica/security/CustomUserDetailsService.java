package com.boticasaludica.security;

import com.boticasaludica.model.Usuario;
import com.boticasaludica.store.MemoriaStore;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemoriaStore memoriaStore;

    public CustomUserDetailsService(MemoriaStore memoriaStore) {
        this.memoriaStore = memoriaStore;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Usuario usuario = memoriaStore.getUsuarios().get(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .disabled(!usuario.isActivo())
                .roles(usuario.getRole().name())
                .build();
    }
}
