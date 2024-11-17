package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto.ReporteDTO;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.service.ExportarService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDateTime;

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

    private ExportarService exportarService = new ExportarService();
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
    @FXML
    public void handleEliminarProducto() {
        Producto seleccionado = productosTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            productosList.remove(seleccionado);
        } else {
            mostrarAlerta("Advertencia", "Debe seleccionar un producto para eliminar", Alert.AlertType.WARNING);
        }
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
    public void handleActualizarUsuario(){
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
    public void handleExportarEstadisticas() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            ReporteDTO reporte = new ReporteDTO(
                    "Reporte de Estadísticas del Marketplace",
                    LocalDateTime.now().toString(),
                    "Administrador",
                    estadisticasArea.getText()
            );
            exportarService.exportarReporte(reporte, file.getAbsolutePath());
            mostrarAlerta("Éxito", "Reporte exportado correctamente", Alert.AlertType.INFORMATION);
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
        // Remover todas las pestañas dinámicas que no sean Productos, Usuarios o Estadísticas
        adminTabPane.getTabs().removeIf(tab -> !tab.getText().equals("Productos") && !tab.getText().equals("Usuarios") && !tab.getText().equals("Estadísticas"));

        for (Vendedor vendedor : modelFactory.getRedSocial().getVendedores()) {
            // Crear una nueva pestaña con el nombre del vendedor
            Tab vendedorTab = new Tab(vendedor.getNombre());

            // Crear un contenedor principal para muro y productos
            AnchorPane contenedorPrincipal = new AnchorPane();

            try {
                // **1. Cargar el Muro del vendedor**
                FXMLLoader muroLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Muro.fxml"));
                AnchorPane muroPane = muroLoader.load();

                // Configurar el controlador del muro y pasar el vendedor actual
                MuroController muroController = muroLoader.getController();
                muroController.setVendedor(vendedor);

                // Configurar tamaño y posición del muro
                muroPane.setLayoutX(10);
                muroPane.setLayoutY(10);
                muroPane.setPrefWidth(400);
                muroPane.setPrefHeight(300);

                // **2. Cargar la Tabla de Productos**
                TableView<Producto> tablaProductos = new TableView<>();
                tablaProductos.setLayoutX(420);
                tablaProductos.setLayoutY(10);
                tablaProductos.setPrefWidth(350);
                tablaProductos.setPrefHeight(300);

                // Configurar las columnas de productos
                TableColumn<Producto, String> nombreColumn = new TableColumn<>("Nombre");
                nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));

                TableColumn<Producto, String> categoriaColumn = new TableColumn<>("Categoría");
                categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));

                TableColumn<Producto, String> estadoColumn = new TableColumn<>("Estado");
                estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

                TableColumn<Producto, Double> precioColumn = new TableColumn<>("Precio");
                precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

                // Agregar las columnas a la tabla
                tablaProductos.getColumns().addAll(nombreColumn, categoriaColumn, estadoColumn, precioColumn);

                // Llenar la tabla con los productos del vendedor
                tablaProductos.setItems(FXCollections.observableArrayList(vendedor.getProductos()));

                // Agregar el muro y la tabla de productos al contenedor principal
                contenedorPrincipal.getChildren().addAll(muroPane, tablaProductos);

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar el contenido para el vendedor " + vendedor.getNombre(), Alert.AlertType.ERROR);
            }

            // Establecer el contenedor principal como contenido de la pestaña
            vendedorTab.setContent(contenedorPrincipal);

            // Agregar la pestaña al TabPane
            adminTabPane.getTabs().add(vendedorTab);
        }
    }

        private void handleAgregarProducto(Vendedor vendedor, TableView<Producto> tablaProductos) {
        Dialog<Producto> dialog = new Dialog<>();
        dialog.setTitle("Agregar Producto");
        dialog.setHeaderText("Agregar un nuevo producto para " + vendedor.getNombre());

        // Crear campos de entrada
        TextField nombreField = new TextField();
        TextField categoriaField = new TextField();
        TextField precioField = new TextField();
        TextField imagenField = new TextField();

        Button seleccionarImagenButton = new Button("Seleccionar Imagen");
        seleccionarImagenButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar Imagen");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg"));
            File archivo = fileChooser.showOpenDialog(null);
            if (archivo != null) {
                imagenField.setText(archivo.getAbsolutePath());
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Categoría:"), 0, 1);
        grid.add(categoriaField, 1, 1);
        grid.add(new Label("Precio:"), 0, 2);
        grid.add(precioField, 1, 2);
        grid.add(new Label("Imagen:"), 0, 3);
        grid.add(imagenField, 1, 3);
        grid.add(seleccionarImagenButton, 2, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType agregarButtonType = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(agregarButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == agregarButtonType) {
                try {
                    double precio = Double.parseDouble(precioField.getText());
                    return new Producto(nombreField.getText(), categoriaField.getText(), precio, "Publicado", imagenField.getText());
                } catch (NumberFormatException e) {
                    mostrarAlerta("Error", "El precio debe ser un número válido", Alert.AlertType.ERROR);
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(nuevoProducto -> {
            vendedor.agregarProducto(nuevoProducto);
            tablaProductos.setItems(FXCollections.observableArrayList(vendedor.getProductos()));
        });
    }
}