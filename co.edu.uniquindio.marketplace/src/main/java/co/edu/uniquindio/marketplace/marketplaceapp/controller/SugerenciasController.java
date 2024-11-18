package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class SugerenciasController {

    @FXML
    private VBox sugerenciasContainer;

    @FXML
    private TextField buscarContactoField;

    @FXML
    private ListView<Vendedor> contactosListView;

    private Vendedor vendedorActual;

    @FXML
    public void initialize() {
        cargarContactos();
        buscarContactoField.textProperty().addListener((observable, oldValue, newValue) -> filtrarSugerencias(newValue));

    }

    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        cargarSugerencias();
    }

    private void cargarSugerencias() {
        sugerenciasContainer.getChildren().clear();

        List<Vendedor> sugerencias = ModelFactory.getInstance().getRedSocial().getVendedores().stream()
                .filter(v -> !vendedorActual.getContactos().contains(v) && !v.equals(vendedorActual))
                .toList();

        for (Vendedor sugerencia : sugerencias) {
            HBox sugerenciaCard = crearSugerenciaCard(sugerencia);
            sugerenciasContainer.getChildren().add(sugerenciaCard);
        }
    }

    private HBox crearSugerenciaCard(Vendedor sugerencia) {
        HBox card = new HBox();
        card.setSpacing(15);
        card.setStyle("-fx-padding: 10; -fx-border-color: #E0E0E0; -fx-border-radius: 5; -fx-background-color: #FFFFFF; -fx-background-radius: 5;");

        Text nombre = new Text(sugerencia.getNombre() + " " + sugerencia.getApellidos());
        nombre.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        Button agregarButton = new Button("Enviar Solicitud");
        agregarButton.setStyle("-fx-background-color: #5B8DEF; -fx-text-fill: white;");
        agregarButton.setOnAction(event -> enviarSolicitud(sugerencia));

        card.getChildren().addAll(nombre, agregarButton);
        return card;
    }

    private void enviarSolicitud(Vendedor sugerencia) {
        vendedorActual.enviarSolicitud(sugerencia);
        mostrarAlerta("Éxito", "Solicitud de contacto enviada a " + sugerencia.getNombre(), Alert.AlertType.INFORMATION);
        cargarSugerencias();
    }

    private void cargarContactos() {
        contactosListView.getItems().clear();
        if (vendedorActual != null) {
            contactosListView.getItems().addAll(vendedorActual.getContactos());
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
    private void filtrarSugerencias(String query) {
        sugerenciasContainer.getChildren().clear();

        List<Vendedor> sugerenciasFiltradas = ModelFactory.getInstance().getRedSocial().getVendedores().stream()
                .filter(v -> !vendedorActual.getContactos().contains(v) &&
                        !v.equals(vendedorActual) &&
                        (v.getNombre().toLowerCase().contains(query.toLowerCase()) ||
                                v.getApellidos().toLowerCase().contains(query.toLowerCase())))
                .toList();

        for (Vendedor sugerencia : sugerenciasFiltradas) {
            HBox sugerenciaCard = crearSugerenciaCard(sugerencia);
            sugerenciasContainer.getChildren().add(sugerenciaCard);
        }
    }
}
