package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto.UsuarioDTO;
import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;


    @FXML
    public void handleLogin() {
        String nombreUsuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        try {
            Vendedor vendedor = obtenerVendedorActual(nombreUsuario);
            if (vendedor.getPassword().equals(contrasena)) {
                redirigirAPaginaPrincipal(vendedor.getRol().name());
            } else {
                mostrarAlerta("Login Fallido", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
            }
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Login Fallido", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void redirigirAPaginaPrincipal(String rol) {
        try {
            String vista = "/co/edu/uniquindio/marketplace/marketplaceapp/";

            if (rol.equalsIgnoreCase("Administrador")) {
                vista += "AdminPanel.fxml";
            } else if (rol.equalsIgnoreCase("Vendedor")) {
                vista += "VendedorPanel.fxml";
            } else {
                throw new IllegalArgumentException("Rol no válido");
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
            Scene scene = new Scene(loader.load());

            if (rol.equalsIgnoreCase("Vendedor")) {
                VendedorController vendedorController = loader.getController();
                vendedorController.setVendedorActual(obtenerVendedorActual(txtUsuario.getText()));
            }

            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Marketplace - " + rol);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la página principal", Alert.AlertType.ERROR);
        }
    }
    private Vendedor obtenerVendedorActual(String username) {
        // Busca al vendedor actual en la lista global de vendedores
        for (Vendedor vendedor : ModelFactory.getInstance().getRedSocial().getVendedores()) {
            if (vendedor.getUsername().equals(username)) {
                return vendedor;
            }
        }
        throw new IllegalArgumentException("Usuario no encontrado");
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
    @FXML
    public void btnIniciarSesion(ActionEvent actionEvent) {
        handleLogin();
    }
}


