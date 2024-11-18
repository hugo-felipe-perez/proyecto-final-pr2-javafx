package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Solicitud;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class SolicitudesController {

    @FXML
    private ListView<Solicitud> solicitudesListView;

    @FXML
    private TextArea detalleSolicitudArea;

    private Vendedor vendedorActual;

    private ObservableList<Solicitud> solicitudes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        ModelFactory.getInstance().setSolicitudesController(this);
        cargarSolicitudes();
    }
    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;

        if (vendedor != null) {
            cargarSolicitudes();
        } else {
            mostrarAlerta("Error", "El vendedor actual no está definido.", Alert.AlertType.ERROR);
        }
    }

    private void cargarSolicitudes() {
        solicitudes.clear();
        if (vendedorActual != null && vendedorActual.getSolicitudesRecibidas() != null) {
            solicitudes.addAll(vendedorActual.getSolicitudesRecibidas());
            solicitudesListView.setItems(solicitudes);
        }

        solicitudesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarDetallesSolicitud(newValue);
            }
        });
    }

    private void mostrarDetallesSolicitud(Solicitud solicitud) {
        detalleSolicitudArea.setText(
                "De: " + solicitud.getOrigen().getNombre() + "\n" +
                        "Fecha: " + solicitud.getFecha() + "\n" +
                        "Estado: " + solicitud.getEstado() + "\n" +
                        "Mensaje: " + (solicitud.getMensaje() != null ? solicitud.getMensaje() : "No hay mensaje")
        );
    }

    @FXML
    public void handleAceptarSolicitud() {
        Solicitud seleccionada = solicitudesListView.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            seleccionada.cambiarEstado("Aceptada");
            vendedorActual.agregarContacto(seleccionada.getOrigen());
            vendedorActual.getSolicitudesRecibidas().remove(seleccionada);

            // Aquí puedes llamar al método de recarga de contactos
            actualizarContactosEnVista();

            cargarSolicitudes();

            mostrarAlerta(
                    "Solicitud aceptada",
                    "Has aceptado la solicitud de " + seleccionada.getOrigen().getNombre(),
                    Alert.AlertType.INFORMATION
            );
        } else {
            mostrarAlerta("Error", "Debes seleccionar una solicitud para aceptarla.", Alert.AlertType.WARNING);
        }
    }

    private void actualizarContactosEnVista() {
        // Supongamos que tienes acceso al controlador de Contactos
        ContactosController contactosController = obtenerContactosController();
        if (contactosController != null) {
            contactosController.cargarContactos();
        }
    }

    // Método para obtener el controlador de contactos
    private ContactosController obtenerContactosController() {

        return ModelFactory.getInstance().getContactosController();
    }
    @FXML
    public void handleRechazarSolicitud() {
        Solicitud seleccionada = solicitudesListView.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            // Cambiar el estado a "Rechazada"
            seleccionada.cambiarEstado("Rechazada");

            // Eliminar la solicitud de la lista de solicitudes recibidas
            vendedorActual.getSolicitudesRecibidas().remove(seleccionada);

            // Recargar la lista de solicitudes
            cargarSolicitudes();

            mostrarAlerta(
                    "Solicitud rechazada",
                    "Has rechazado la solicitud de " + seleccionada.getOrigen().getNombre(),
                    Alert.AlertType.INFORMATION
            );
        } else {
            mostrarAlerta("Error", "Debes seleccionar una solicitud para rechazarla.", Alert.AlertType.WARNING);
        }
    }
    private void manejarSolicitud(String nuevoEstado) {
        Solicitud seleccionada = solicitudesListView.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            // Cambiar el estado de la solicitud
            seleccionada.cambiarEstado(nuevoEstado);

            if ("Aceptada".equals(nuevoEstado)) {
                // Si la solicitud es aceptada, agregar el origen a los contactos
                vendedorActual.agregarContacto(seleccionada.getOrigen());
            }

            // Eliminar la solicitud de la lista de solicitudes recibidas
            vendedorActual.getSolicitudesRecibidas().remove(seleccionada);

            // Recargar las solicitudes
            cargarSolicitudes();

            mostrarAlerta(
                    "Solicitud " + nuevoEstado.toLowerCase(),
                    "Has " + (nuevoEstado.equals("Aceptada") ? "aceptado" : "rechazado") +
                            " la solicitud de " + seleccionada.getOrigen().getNombre(),
                    Alert.AlertType.INFORMATION
            );
        } else {
            mostrarAlerta("Error", "Debes seleccionar una solicitud.", Alert.AlertType.WARNING);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

}
