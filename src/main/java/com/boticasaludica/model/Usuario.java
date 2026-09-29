package com.boticasaludica.model;

public class Usuario {

    private Long id;
    private String username;
    private String password;
    private Role role;
    private boolean activo;

    public Usuario() {
    }

    public Usuario(Long id, String username, String password, Role role, boolean activo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
