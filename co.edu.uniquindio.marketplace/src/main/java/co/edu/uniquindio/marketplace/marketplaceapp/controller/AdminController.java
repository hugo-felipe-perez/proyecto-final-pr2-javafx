package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto.ReporteDTO;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.services.ExportarServicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class AdminController {

    @FXML
    private TabPane adminTabPane;

    @FXML
    private TableView<Producto> productosTable;

    @FXML
    private TableColumn<Producto, String> nombreColumn;

    @FXML
    private TableColumn<Producto, String> categoriaColumn;

    @FXML
    private TableColumn<Producto, String> estadoColumn;

    @FXML
    private TableColumn<Producto, Double> precioColumn;

    @FXML
    private TableView<Vendedor> usuariosTable;

    @FXML
    private TableColumn<Vendedor, String> nombreUsuarioColumn;
    @FXML
    private TableColumn<Vendedor, String> apellidosUsuarioColumn;
    @FXML
    private TableColumn<Vendedor, String> cedulaUsuarioColumn;
    @FXML
    private TableColumn<Vendedor, String> direccionUsuarioColumn;
    @FXML
    private TableColumn<Vendedor, String> usernameColumn;
    @FXML
    private TableColumn<Vendedor, String> passwordnameColumn;
    @FXML
    private TableColumn<Vendedor, String> rolUsuarioColumn;


    @FXML
    private TextArea estadisticasArea;

    private ObservableList<Producto> productosList = FXCollections.observableArrayList();
    private ObservableList<Vendedor> usuariosList = FXCollections.observableArrayList();

    private ExportarServicio exportarService = new ExportarServicio();
    private ModelFactory modelFactory = ModelFactory.getInstance();

    @FXML
    public void initialize() {
        // Configurar columnas de productos
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Configurar columnas de usuarios
        nombreUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidosUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        cedulaUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        direccionUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordnameColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        rolUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("rol"));

        // Cargar datos iniciales
        cargarProductos();
        cargarUsuarios();
        cargarVendedoresEnTabs();
        cargarEstadisticas(); // Llamar al método para cargar estadísticas
    }

    private void cargarProductos() {
        productosList.clear();
        for (Vendedor vendedor : modelFactory.getRedSocial().getVendedores()) {
            productosList.addAll(vendedor.getProductos());
        }
        productosTable.setItems(productosList);
    }

    private void cargarUsuarios() {
        usuariosList.clear();
        usuariosList.addAll(modelFactory.getRedSocial().getVendedores()); // Agregar vendedores
        usuariosTable.setItems(usuariosList); // Asignar lista a la tabla
    }

    private void cargarEstadisticas() {
        StringBuilder estadisticas = new StringBuilder("Estadísticas Generales del Marketplace:\n");

        // Obtener la lista de vendedores
        List<Vendedor> vendedores = modelFactory.getRedSocial().getVendedores();

        // Agregar estadísticas generales
        estadisticas.append("- Total de usuarios registrados: ").append(vendedores.size()).append("\n");
        estadisticas.append("- Total de productos publicados: ").append(productosList.size()).append("\n\n");

        // Agregar estadísticas específicas de cada vendedor
        for (Vendedor vendedor : vendedores) {
            estadisticas.append("Usuario: ").append(vendedor.getNombre()).append(" (").append(vendedor.getUsername()).append(")\n");
            estadisticas.append("  - Productos publicados: ").append(vendedor.getProductos().size()).append("\n");
            estadisticas.append("  - Mensajes enviados: ").append(vendedor.getMensajesEnviados().size()).append("\n");
            estadisticas.append("  - Mensajes recibidos: ").append(vendedor.getMensajesRecibidos().size()).append("\n");
            estadisticas.append("  - Contactos agregados: ").append(vendedor.getContactos().size()).append("\n\n");
        }

        estadisticasArea.setText(estadisticas.toString());
    }

    @FXML
    public void handleAgregarUsuario() {
        try {
            // Cargar la vista del formulario desde un archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/AgregarUsuario.fxml"));
            DialogPane dialogPane = loader.load();

            // Crear un diálogo para el formulario
            Dialog<Vendedor> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Agregar Nuevo Usuario");

            // Obtener el controlador del formulario
            AgregarUsuarioController controller = loader.getController();

            // Configurar el botón de confirmación
            ButtonType agregarButtonType = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(agregarButtonType, ButtonType.CANCEL);

            // Manejar el resultado del diálogo
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == agregarButtonType) {
                    try {
                        return controller.obtenerNuevoUsuario();
                    } catch (IllegalArgumentException e) {
                        mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
                        return null;
                    }
                }
                return null;
            });

            // Mostrar el diálogo y obtener el resultado
            dialog.showAndWait().ifPresent(nuevoUsuario -> {
                usuariosList.add(nuevoUsuario);
                modelFactory.getRedSocial().agregarVendedor(nuevoUsuario);

                // Actualizar dinámicamente las pestañas de vendedores
                cargarVendedoresEnTabs();

                mostrarAlerta("Éxito", "El usuario ha sido agregado exitosamente", Alert.AlertType.INFORMATION);
            });
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el formulario para agregar un usuario", Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void handleActualizarUsuario() {
        Vendedor seleccionado = usuariosTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                // Cargar la vista del formulario para editar usuario
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/EditarUsuario.fxml"));
                DialogPane dialogPane = loader.load();

                // Crear un diálogo para el formulario
                Dialog<Vendedor> dialog = new Dialog<>();
                dialog.setDialogPane(dialogPane);
                dialog.setTitle("Actualizar Usuario");

                // Obtener el controlador del formulario
                ActualizarUsuarioController controller = loader.getController();
                controller.setUsuario(seleccionado); // Pasar el usuario seleccionado al controlador

                // Configurar el botón de confirmación
                ButtonType actualizarButtonType = new ButtonType("Actualizar", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(actualizarButtonType, ButtonType.CANCEL);

                // Manejar el resultado del diálogo
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == actualizarButtonType) {
                        try {
                            return controller.obtenerUsuarioActualizado();
                        } catch (IllegalArgumentException e) {
                            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
                            return null;
                        }
                    }
                    return null;
                });

                // Mostrar el diálogo y obtener el resultado
                dialog.showAndWait().ifPresent(usuarioActualizado -> {
                    // Refrescar la tabla para reflejar los cambios
                    usuariosTable.refresh();
                    mostrarAlerta("Éxito", "El usuario ha sido actualizado correctamente", Alert.AlertType.INFORMATION);
                });
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo abrir el formulario para actualizar el usuario", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Debe seleccionar un usuario para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void handleEliminarUsuario() {
        Vendedor seleccionado = usuariosTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            usuariosList.remove(seleccionado);
            modelFactory.getRedSocial().getVendedores().remove(seleccionado);

            // Actualizar dinámicamente las pestañas de vendedores
            cargarVendedoresEnTabs();

            mostrarAlerta("Éxito", "El usuario ha sido eliminado exitosamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Advertencia", "Debe seleccionar un usuario para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void handleCerrarSesion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Login.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage currentStage = (Stage) adminTabPane.getScene().getWindow();
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login - Marketplace");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cerrar la sesión correctamente", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleExportarEstadisticas() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                // Escribe el contenido de las estadísticas en el archivo seleccionado
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("Reporte de Estadísticas del Marketplace\n");
                    writer.write("Generado el: " + LocalDateTime.now() + "\n");
                    writer.write("\n--- Contenido ---\n");
                    writer.write(estadisticasArea.getText());
                }
                mostrarAlerta("Éxito", "Reporte exportado correctamente a " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo guardar el reporte: " + e.getMessage(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Advertencia", "No se seleccionó ningún archivo para guardar el reporte.", Alert.AlertType.WARNING);
        }
    }


    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    private void cargarVendedoresEnTabs() {
        // Limpiar tabs de los vendedores existentes
        adminTabPane.getTabs().removeIf(tab -> !tab.getText().equals("Productos") && !tab.getText().equals("Usuarios") && !tab.getText().equals("Estadísticas"));

        for (Vendedor vendedor : modelFactory.getRedSocial().getVendedores()) {
            // Crear un Tab principal con el nombre del vendedor
            Tab vendedorTab = new Tab(vendedor.getNombre());

            // Crear un TabPane interno para contener "Muro" y "Productos"
            TabPane subTabPane = new TabPane();

            // Crear sub-pestaña de Muro
            Tab muroTab = new Tab("Muro");
            try {
                FXMLLoader muroLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Muro.fxml"));
                BorderPane muroPane = muroLoader.load();

                // Pasar el vendedor actual al MuroController
                MuroController muroController = muroLoader.getController();
                muroController.setVendedorActual(vendedor);

                muroTab.setContent(muroPane);
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo cargar el muro del vendedor: " + vendedor.getNombre(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }

            // Crear sub-pestaña de Productos
            Tab productosTab = new Tab("Productos");
            try {
                FXMLLoader productosLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Productos.fxml"));
                AnchorPane productosPane = productosLoader.load();

                // Pasar el vendedor actual al ProductosController (debes asegurarte de que este controlador tenga un método setVendedorActual)
                ProductosController productosController = productosLoader.getController();
                productosController.setVendedorActual(vendedor);

                productosTab.setContent(productosPane);
            } catch (Exception e) {
                mostrarAlerta("Error", "No se pudo cargar los productos del vendedor: " + vendedor.getNombre(), Alert.AlertType.ERROR);
                e.printStackTrace();
            }

            // Agregar las sub-pestañas al TabPane interno
            subTabPane.getTabs().addAll(muroTab, productosTab);

            // Establecer el TabPane interno como contenido del Tab principal del vendedor
            vendedorTab.setContent(subTabPane);

            // Agregar el Tab principal al TabPane del administrador
            adminTabPane.getTabs().add(vendedorTab);
        }
    }

    @FXML
    public void handleEstadisticasAvanzadas(ActionEvent actionEvent) {
        try {
            // Cargar el archivo FXML de Estadísticas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Estadisticas.fxml"));
            Parent root = loader.load();

            // Obtener la ventana actual desde el botón o la acción
            Stage currentStage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            // Configurar la nueva escena
            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle("Estadísticas Avanzadas"); // Título de la ventana
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la pantalla de Estadísticas Avanzadas.", Alert.AlertType.ERROR);
        }
    }

}