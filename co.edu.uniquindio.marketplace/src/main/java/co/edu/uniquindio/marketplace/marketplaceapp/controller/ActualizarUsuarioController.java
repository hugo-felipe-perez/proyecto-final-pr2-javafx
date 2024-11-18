package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ActualizarUsuarioController {

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

    private Vendedor usuario;

    public void setUsuario(Vendedor usuario) {
        this.usuario = usuario;
        if (usuario != null) {
            nombreField.setText(usuario.getNombre());
            apellidosField.setText(usuario.getApellidos());
            cedulaField.setText(usuario.getCedula());
            direccionField.setText(usuario.getDireccion());
            usernameField.setText(usuario.getUsername());
            passwordField.setText(usuario.getPassword());
        }
    }

    public Vendedor obtenerUsuarioActualizado() {
        if (!validarCampos()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios y deben ser válidos.");
        }

        usuario.setNombre(nombreField.getText());
        usuario.setApellidos(apellidosField.getText());
        usuario.setCedula(cedulaField.getText());
        usuario.setDireccion(direccionField.getText());
        usuario.setUsername(usernameField.getText());
        usuario.setPassword(passwordField.getText());

        return usuario;
    }

    private boolean validarCampos() {
        if (nombreField.getText().isEmpty() ||
                apellidosField.getText().isEmpty() ||
                cedulaField.getText().isEmpty() ||
                direccionField.getText().isEmpty() ||
                usernameField.getText().isEmpty() ||
                passwordField.getText().isEmpty()) {
            mostrarAlerta("Error", "Por favor complete todos los campos.", Alert.AlertType.ERROR);
            return false;
        }

        if (!cedulaField.getText().matches("\\d+")) {
            mostrarAlerta("Error", "La cédula debe contener solo números.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
