package co.edu.uniquindio.marketplace.marketplaceapp.model;

import co.edu.uniquindio.marketplace.marketplaceapp.enums.Rol;

import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Usuario {
    private List<Vendedor> contactos;
    private List<Producto> productos;
    private List<Solicitud> solicitudesRecibidas; // Solicitudes enviadas a este vendedor
    private List<Notificacion> notificaciones;

    public Vendedor(String nombre, String apellidos, String cedula, String direccion, String username, String password, Rol rol) {
        super(nombre, apellidos, cedula, direccion, username, password, rol);
        this.contactos = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.solicitudesRecibidas = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
    }

    // Métodos para contactos
    public List<Vendedor> getContactos() {
        return contactos;
    }

    public void agregarContacto(Vendedor vendedor) {
        if (vendedor == null || this.equals(vendedor)) return;
        if (!contactos.contains(vendedor) && contactos.size() < 10) {
            contactos.add(vendedor);
            vendedor.contactos.add(this); // Relación bidireccional
        }
    }

    public void eliminarContacto(Vendedor vendedor) {
        if (contactos.remove(vendedor)) {
            vendedor.contactos.remove(this); // Relación bidireccional
        }
    }

    // Métodos para productos
    public List<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void enviarSolicitud(Vendedor destinatario) {
        if (destinatario == null || this.equals(destinatario)) {
            System.out.println("No puedes enviar una solicitud a ti mismo o a un destinatario nulo.");
            return;
        }

        if (contactos.contains(destinatario)) {
            System.out.println("Ya son contactos. No se puede enviar la solicitud.");
            return;
        }

        Solicitud nuevaSolicitud = new Solicitud(this, destinatario, "Me gustaría agregarte como contacto.");

        if (destinatario.getSolicitudesRecibidas().contains(nuevaSolicitud)) {
            System.out.println("Ya existe una solicitud pendiente entre " + this.getUsername() + " y " + destinatario.getUsername());
            return;
        }

        destinatario.getSolicitudesRecibidas().add(nuevaSolicitud);
        System.out.println("Solicitud enviada correctamente de " + this.getUsername() + " a " + destinatario.getUsername());
    }
    public void aceptarSolicitud(Vendedor origen) {
        Solicitud solicitud = solicitudesRecibidas.stream()
                .filter(s -> s.getOrigen().equals(origen))
                .findFirst()
                .orElse(null);

        if (solicitud != null) {
            agregarContacto(origen);
            solicitudesRecibidas.remove(solicitud);
            System.out.println("Solicitud aceptada de " + origen.getUsername());
        } else {
            System.out.println("No hay solicitud de este usuario.");
        }
    }

    public List<Solicitud> getSolicitudesRecibidas() {
        return solicitudesRecibidas;
    }

    // Notificaciones
    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void agregarNotificacion(Notificacion notificacion) {
        notificaciones.add(notificacion);
    }

    @Override
    public String toString() {
        return String.format("Vendedor: %s %s (%s)", getNombre(), getApellidos(), getUsername());
    }
    public void recibirSolicitud(Vendedor origen) {
        Solicitud solicitud = new Solicitud(origen, this, "Me gustaría agregarte como contacto.");
        if (!solicitudesRecibidas.contains(solicitud)) {
            solicitudesRecibidas.add(solicitud);
            System.out.println("Solicitud recibida de " + origen.getUsername());
        } else {
            System.out.println("Solicitud duplicada de " + origen.getUsername());
        }
    }

}