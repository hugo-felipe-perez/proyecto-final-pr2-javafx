package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.enums.Rol;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AgregarUsuarioController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidosField;

    @FXML
    private TextField cedulaField;

    @FXML
    private TextField direccionField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<Rol> rolChoiceBox;

    @FXML
    public void initialize() {
        // Cargar roles en el ChoiceBox
        rolChoiceBox.getItems().addAll(Rol.values());
    }

    public Vendedor obtenerNuevoUsuario() {
        // Validar campos obligatorios
        if (!validarCampos()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios y deben ser válidos.");
        }

        // Crear y devolver un nuevo vendedor
        return new Vendedor(
                nombreField.getText(),
                apellidosField.getText(),
                cedulaField.getText(),
                direccionField.getText(),
                usernameField.getText(),
                passwordField.getText(),
                rolChoiceBox.getValue()
        );
    }

    private boolean validarCampos() {
        // Verificar si hay campos vacíos
        if (nombreField.getText().isEmpty() ||
                apellidosField.getText().isEmpty() ||
                cedulaField.getText().isEmpty() ||
                direccionField.getText().isEmpty() ||
                usernameField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                rolChoiceBox.getValue() == null) {
            mostrarAlerta("Error", "Por favor complete todos los campos.", AlertType.ERROR);
            return false;
        }
        // Validar que la cédula sea numérica
        if (!cedulaField.getText().matches("\\d+")) {
            mostrarAlerta("Error", "La cédula debe contener solo números.", AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
