package co.edu.uniquindio.marketplace.marketplaceapp.factory;

import co.edu.uniquindio.marketplace.marketplaceapp.controller.ContactosController;
import co.edu.uniquindio.marketplace.marketplaceapp.controller.MensajesController;
import co.edu.uniquindio.marketplace.marketplaceapp.controller.SolicitudesController;
import co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto.AdministradorDTO;
import co.edu.uniquindio.marketplace.marketplaceapp.model.*;
import co.edu.uniquindio.marketplace.marketplaceapp.utils.DataUtil;

import java.time.LocalDateTime;
import java.util.List;

public class ModelFactory {
    private static ModelFactory instance;
    private final RedSocial redSocial;

    private Vendedor usuarioActual;
    private ContactosController contactosController;
    private SolicitudesController solicitudesController;
    private MensajesController mensajesController;
    private List<AdministradorDTO> administradores;

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

    public Vendedor getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Vendedor usuarioActual) {
        this.usuarioActual = usuarioActual;
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

    public void setMensajesController(MensajesController controller) {
        this.mensajesController = controller;
    }

    public MensajesController getMensajesController() {
        return mensajesController;
    }

    // Método para enviar solicitudes
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

    public void enviarMensaje(Vendedor remitente, Vendedor destinatario, String contenido) {
        if (remitente == null || destinatario == null) {
            System.out.println("Remitente o destinatario no pueden ser nulos.");
            return;
        }

        if (!remitente.getContactos().contains(destinatario)) {
            System.out.println("No puedes enviar mensajes a un usuario que no es tu contacto.");
            return;
        }

        Mensaje mensaje = new Mensaje(remitente, destinatario, contenido);
        remitente.getMensajesEnviados().add(mensaje);
        destinatario.getMensajesRecibidos().add(mensaje);

        // Notificación para el destinatario
        enviarNotificacion(destinatario, "Nuevo mensaje",
                "Has recibido un mensaje de " + remitente.getNombre());

        System.out.println("Mensaje enviado de " + remitente.getUsername() + " a " + destinatario.getUsername());
    }

    private void enviarNotificacion(Vendedor vendedor, String tipo, String mensaje) {
        vendedor.getNotificaciones().add(new Notificacion(tipo, mensaje, LocalDateTime.now()));
    }
    public List<AdministradorDTO> getAdministradores() {
        return DataUtil.cargarAdministradoresIniciales();
    }


    private void inicializarDatos() {
        redSocial.getVendedores().addAll(DataUtil.cargarVendedoresIniciales());
    }
}
