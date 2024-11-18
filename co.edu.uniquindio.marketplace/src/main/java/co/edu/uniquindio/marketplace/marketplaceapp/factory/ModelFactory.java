package co.edu.uniquindio.marketplace.marketplaceapp.factory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.*;
import co.edu.uniquindio.marketplace.marketplaceapp.utils.DataUtil;
import co.edu.uniquindio.marketplace.marketplaceapp.controller.ContactosController;
import co.edu.uniquindio.marketplace.marketplaceapp.controller.SolicitudesController;
import java.time.LocalDateTime;

public class ModelFactory {
    private static ModelFactory instance;
    private final RedSocial redSocial;

    private Vendedor usuarioActual;
    private ContactosController contactosController;
    private SolicitudesController solicitudesController;
    public Vendedor getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Vendedor usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    private ModelFactory() {
        this.redSocial = RedSocial.getInstance();
        inicializarDatos();
    }

    public static ModelFactory getInstance() {
        if (instance == null) {
            instance = new ModelFactory();
        }
        return instance;
    }

    public RedSocial getRedSocial() {
        return redSocial;
    }

    // Método para enviar solicitud
    public void enviarSolicitud(Vendedor origen, Vendedor destino) {
        if (origen == null || destino == null) {
            System.out.println("Origen o destino no pueden ser nulos.");
            return;
        }

        if (origen.equals(destino)) {
            System.out.println("No puedes enviar una solicitud a ti mismo.");
            return;
        }

        if (destino.getContactos().contains(origen)) {
            System.out.println("Ya son contactos. No se puede enviar la solicitud.");
            return;
        }

        Solicitud nuevaSolicitud = new Solicitud(origen, destino, "Me gustaría agregarte como contacto.");
        if (!destino.getSolicitudesRecibidas().contains(nuevaSolicitud)) {
            destino.recibirSolicitud(origen);
            enviarNotificacion(destino, "Solicitud de contacto",
                    "Has recibido una solicitud de contacto de " + origen.getNombre());
            System.out.println("Solicitud enviada de " + origen.getUsername() + " a " + destino.getUsername());
        } else {
            System.out.println("Ya existe una solicitud pendiente entre " + origen.getUsername() + " y " + destino.getUsername());
        }
    }

    private void enviarNotificacion(Vendedor vendedor, String tipo, String mensaje) {
        vendedor.getNotificaciones().add(new Notificacion(tipo, mensaje, LocalDateTime.now()));
    }
    public void setContactosController(ContactosController controller) {
        this.contactosController = controller;
    }

    public ContactosController getContactosController() {
        return contactosController;
    }

    public void setSolicitudesController(SolicitudesController controller) {
        this.solicitudesController = controller;
    }

    public SolicitudesController getSolicitudesController() {
        return solicitudesController;
    }
    private void inicializarDatos() {
        redSocial.getVendedores().addAll(DataUtil.cargarVendedoresIniciales());
    }

}
