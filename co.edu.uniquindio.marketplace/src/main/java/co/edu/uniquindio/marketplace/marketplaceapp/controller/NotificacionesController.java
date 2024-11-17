package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class NotificacionesController {

    @FXML
    private TableView<Notificacion> notificacionesTable;

    @FXML
    private TableColumn<Notificacion, String> tipoColumn;

    @FXML
    private TableColumn<Notificacion, String> mensajeColumn;

    @FXML
    private Button cerrarButton;

    private ObservableList<Notificacion> notificacionesList = FXCollections.observableArrayList();

    public NotificacionesController(Button cerrarButton) {
        this.cerrarButton = cerrarButton;
    }

    @FXML
    public void initialize() {
        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        mensajeColumn.setCellValueFactory(new PropertyValueFactory<>("mensaje"));
        notificacionesTable.setItems(notificacionesList);
    }

    public void agregarNotificacion(String tipo, String mensaje) {
        notificacionesList.add(new Notificacion(tipo, mensaje));
    }

    @FXML
    public void handleCerrar() {
        Stage stage = (Stage) cerrarButton.getScene().getWindow();
        stage.close();
    }

    public static class Notificacion {
        private final String tipo;
        private final String mensaje;

        public Notificacion(String tipo, String mensaje) {
            this.tipo = tipo;
            this.mensaje = mensaje;
        }

        public String getTipo() {
            return tipo;
        }

        public String getMensaje() {
            return mensaje;
        }
    }
}
