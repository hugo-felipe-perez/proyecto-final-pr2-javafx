package co.edu.uniquindio.marketplace.marketplaceapp.model.builder;

import co.edu.uniquindio.marketplace.marketplaceapp.enums.Rol;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Usuario;

public class UsuarioBuilder {
    private String nombre;
    private String apellidos;
    private String cedula;
    private String direccion;
    private String username;
    private String password;
    private Rol rol;

    public UsuarioBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public UsuarioBuilder setApellidos(String apellidos) {
        this.apellidos = apellidos;
        return this;
    }

    public UsuarioBuilder setCedula(String cedula) {
        this.cedula = cedula;
        return this;
    }

    public UsuarioBuilder setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public UsuarioBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UsuarioBuilder setPassword(String password) {
        this.password = password;
        return this;
    }
    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }



    public Usuario build() {
        return new Usuario(nombre, apellidos, cedula, direccion, username, password, rol);
    }
}
