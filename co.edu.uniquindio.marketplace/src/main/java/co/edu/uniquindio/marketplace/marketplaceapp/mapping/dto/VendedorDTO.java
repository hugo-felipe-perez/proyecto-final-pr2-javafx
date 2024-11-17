package co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto;

import java.util.List;

public class VendedorDTO {
    private String nombre;
    private String apellidos;
    private List<String> contactos; // Nombres de los contactos
    private List<String> productos; // Nombres de los productos

    public VendedorDTO(String nombre, String apellidos, List<String> contactos, List<String> productos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contactos = contactos;
        this.productos = productos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public List<String> getContactos() {
        return contactos;
    }

    public List<String> getProductos() {
        return productos;
    }

    @Override
    public String toString() {
        return "VendedorDTO{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", contactos=" + contactos +
                ", productos=" + productos +
                '}';
    }
}
