package co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto;

public class UsuarioDTO {
    private String nombre;
    private String username;
    private String rol;
    private String password;

    public UsuarioDTO(String nombre, String username, String rol, String password) {
        this.nombre = nombre;
        this.username = username;
        this.rol = rol;
        this.password= password;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public boolean autenticar(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "nombre='" + nombre + '\'' +
                ", username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
