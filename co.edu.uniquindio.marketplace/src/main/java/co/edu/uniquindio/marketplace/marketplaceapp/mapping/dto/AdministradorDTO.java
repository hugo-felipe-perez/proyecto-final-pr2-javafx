package co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto;

import co.edu.uniquindio.marketplace.marketplaceapp.enums.Rol;

public class AdministradorDTO {
    private String usuario;
    private String contrasena;
    private Rol rol;

    public AdministradorDTO() {
    }

    public AdministradorDTO(String usuario, String contrasena,Rol rol) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol=rol;
    }


    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    // Método toString para imprimir información del administrador
    @Override
    public String toString() {
        return "AdministradorDTO{" +
                "usuario='" + usuario + '\'' +
                ", contraseña='" + contrasena + '\'' +
                ", rol=" + rol +
                '}';
    }
}
