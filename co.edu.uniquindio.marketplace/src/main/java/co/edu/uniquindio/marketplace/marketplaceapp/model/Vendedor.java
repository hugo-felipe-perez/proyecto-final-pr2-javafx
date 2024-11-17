package co.edu.uniquindio.marketplace.marketplaceapp.model;

import co.edu.uniquindio.marketplace.marketplaceapp.enums.Rol;

import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Usuario {
    private List<Vendedor> contactos;
    private List<Producto> productos;

    public Vendedor(String nombre, String apellidos, String cedula, String direccion, String username, String password, Rol rol) {
        super(nombre, apellidos, cedula, direccion, username, password, rol);
        this.contactos = new ArrayList<>();
        this.productos = new ArrayList<>();
    }


    // Métodos para contactos
    public List<Vendedor> getContactos() {
        return contactos;
    }

    public void agregarContacto(Vendedor vendedor) {
        if (vendedor == null || this.equals(vendedor)) {
            return; // No se puede agregar a sí mismo o un vendedor nulo
        }
        if (!contactos.contains(vendedor) && contactos.size() < 10) {
            contactos.add(vendedor);
            if (!vendedor.getContactos().contains(this)) {
                vendedor.getContactos().add(this); // Bidireccional solo si aún no existe
            }
        }
    }
    public void eliminarContacto(Vendedor vendedor) {
        if (contactos.contains(vendedor)) {
            contactos.remove(vendedor);
            vendedor.getContactos().remove(this); // Bidireccional
        }
    }

    // Métodos para productos
    public List<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }
    @Override
    public String toString() {
        return String.format("Vendedor: %s %s (%s)", getNombre(), getApellidos(), getUsername());
    }


}