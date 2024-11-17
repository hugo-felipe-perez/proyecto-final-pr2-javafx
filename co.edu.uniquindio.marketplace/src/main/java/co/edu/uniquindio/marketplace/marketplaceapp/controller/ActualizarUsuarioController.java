package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import java.time.LocalDateTime;

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
    private TextField passwordField;

    private Vendedor usuario;

    public void setUsuario(Vendedor usuario) {
        this.usuario = usuario;
        nombreField.setText(usuario.getNombre());
        apellidosField.setText(usuario.getApellidos());
        cedulaField.setText(usuario.getCedula());
        direccionField.setText(usuario.getDireccion());
        usernameField.setText(usuario.getUsername());
        passwordField.setText(usuario.getPassword());
    }

    public Vendedor obtenerUsuarioActualizado() {
        usuario.setNombre(nombreField.getText());
        usuario.setApellidos(apellidosField.getText());
        usuario.setCedula(cedulaField.getText());
        usuario.setDireccion(direccionField.getText());
        usuario.setUsername(usernameField.getText());
        usuario.setPassword(passwordField.getText());
        return usuario;
    }
}

