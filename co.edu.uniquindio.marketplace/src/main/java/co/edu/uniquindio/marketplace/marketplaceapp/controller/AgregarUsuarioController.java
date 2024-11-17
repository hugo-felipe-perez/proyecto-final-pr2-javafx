package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.enums.Rol;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        rolChoiceBox.getItems().addAll(Rol.values());
    }
    public Vendedor obtenerNuevoUsuario() {
        // Validar campos obligatorios
        if (nombreField.getText().isEmpty() || apellidosField.getText().isEmpty() || cedulaField.getText().isEmpty() ||
                direccionField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty() ||
                rolChoiceBox.getValue() == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

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
}
