package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Mensaje;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class MensajesController {
    @FXML
    private ListView<Mensaje> mensajesListView;

    @FXML
    private TextArea detalleMensajeArea;

    @FXML
    private TextField mensajeField;

    private Vendedor vendedorActual;

    private ObservableList<Mensaje> mensajes = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        if (mensajesListView != null && detalleMensajeArea != null) {
            mensajesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    mostrarDetalleMensaje(newValue);
                }
            });
        } else {
            System.out.println("Error al inicializar la interfaz gráfica.");
        }
    }
    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        cargarMensajes();
    }

    private void cargarMensajes() {
        mensajes.clear();
        mensajes.addAll(vendedorActual.getMensajesRecibidos());
        mensajesListView.setItems(mensajes);

        mensajesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarDetalleMensaje(newValue);
            }
        });
    }

    private void mostrarDetalleMensaje(Mensaje mensaje) {
        detalleMensajeArea.setText(
                "De: " + mensaje.getRemitente().getNombre() + "\n" +
                        "Para: " + mensaje.getDestinatario().getNombre() + "\n" +
                        "Contenido: " + mensaje.getContenido()
        );
    }

    @FXML
    public void handleEnviarMensaje() {
        if (vendedorActual == null) {
            mostrarAlerta("Error", "Vendedor actual no definido.", Alert.AlertType.ERROR);
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enviar Mensaje");
        dialog.setHeaderText("Enviar mensaje a un contacto");
        dialog.setContentText("Escribe el nombre de usuario del destinatario:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(username -> {
            Vendedor destinatario = buscarVendedorPorUsername(username);
            if (destinatario != null) {
                String contenidoMensaje = mensajeField.getText();
                if (contenidoMensaje == null || contenidoMensaje.trim().isEmpty()) {
                    mostrarAlerta("Error", "El mensaje no puede estar vacío.", Alert.AlertType.WARNING);
                    return;
                }

                ModelFactory.getInstance().enviarMensaje(vendedorActual, destinatario, contenidoMensaje);
                mostrarAlerta("Éxito", "Mensaje enviado a " + destinatario.getNombre(), Alert.AlertType.INFORMATION);
                mensajeField.clear();
                cargarMensajes();
            } else {
                mostrarAlerta("Error", "Usuario no encontrado.", Alert.AlertType.ERROR);
            }
        });
    }

    private Vendedor buscarVendedorPorUsername(String username) {
        return ModelFactory.getInstance().getRedSocial().getVendedores().stream()
                .filter(v -> v.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
