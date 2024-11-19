package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDateTime;

public class ProductosController {

    @FXML
    private TableView<Producto> productosTable;

    @FXML
    private TableColumn<Producto, String> nombreColumn;

    @FXML
    private TableColumn<Producto, String> categoriaColumn;

    @FXML
    private TableColumn<Producto, Double> precioColumn;

    @FXML
    private TableColumn<Producto, String> imagenColumn;

    @FXML
    private TableColumn<?, ?> estadoColum;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField categoriaField;

    @FXML
    private TextField precioField;

    @FXML
    private TextField imagenPathField;

    @FXML
    private TextField estadoField;

    @FXML
    private Label mensajeError;
    @FXML
    private ImageView productosPanel;

    private ObservableList<Producto> productosList = FXCollections.observableArrayList();
    private ModelFactory modelFactory = ModelFactory.getInstance();
    private Vendedor vendedorActual;


    @FXML
    public void initialize() {
        // Configurar columnas
        try {
            // Configurar la imagen de fondo
            Image background = new Image(getClass().getResource("/images/productos.png").toExternalForm());
            productosPanel.setImage(background);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
        }
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        estadoColum.setCellValueFactory(new PropertyValueFactory<>("estado")); // Configuración de la columna estado
        cargarProductos();
    }

    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        cargarProductos();
    }

    private void cargarProductos() {
        if (vendedorActual != null) {
            productosList.setAll(vendedorActual.getProductos());
            productosTable.setItems(productosList);
        }
    }

    @FXML
    public void handleSeleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg")
        );
        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            imagenPathField.setText(archivoSeleccionado.getAbsolutePath());
        }
    }

    @FXML
    public void handlePublicarProducto() {
        try {
            // Validaciones
            String nombre = nombreField.getText();
            String categoria = categoriaField.getText();
            String imagenPath = imagenPathField.getText(); // Obtiene la ruta como String
            double precio = Double.parseDouble(precioField.getText());
            String estado = estadoField.getText(); // Obtiene la ruta como String
            LocalDateTime fechaPublicacion = LocalDateTime.now();

            if (nombre.isEmpty() || categoria.isEmpty() || imagenPath.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }

            // Crear objeto Image
            Image imagen = new Image("file:" + imagenPath);

            // Crear y agregar producto
            Producto producto = new Producto(nombre, categoria, precio, "Publicado", imagen,fechaPublicacion);
            vendedorActual.agregarProducto(producto);
            cargarProductos();

            // Limpiar campos
            nombreField.clear();
            categoriaField.clear();
            precioField.clear();
            imagenPathField.clear();
            estadoField.clear();
            mensajeError.setText("");

        } catch (NumberFormatException e) {
            mensajeError.setText("El precio debe ser un número válido.");
        } catch (IllegalArgumentException e) {
            mensajeError.setText(e.getMessage());
        }
    }

    @FXML
    public void handleEliminarProducto() {
        Producto seleccionado = productosTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            vendedorActual.getProductos().remove(seleccionado);
            cargarProductos();
        } else {
            mensajeError.setText("Debe seleccionar un producto para eliminar.");
        }
    }
    @FXML
    public void handleActualizarProducto() {
        Producto seleccionado = productosTable.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                // Validaciones
                String nuevoNombre = nombreField.getText();
                String nuevaCategoria = categoriaField.getText();
                String nuevoImagenPath = imagenPathField.getText(); // Obtiene la ruta como String
                double nuevoPrecio = Double.parseDouble(precioField.getText());
                String nuevoEstado = estadoField.getText();

                if (nuevoNombre.isEmpty() || nuevaCategoria.isEmpty() || nuevoImagenPath.isEmpty()) {
                    throw new IllegalArgumentException("Todos los campos son obligatorios.");
                }

                // Crear objeto Image
                Image nuevaImagen = new Image("file:" + nuevoImagenPath);

                // Actualizar los valores del producto seleccionado
                seleccionado.setNombre(nuevoNombre);
                seleccionado.setCategoria(nuevaCategoria);
                seleccionado.setPrecio(nuevoPrecio);
                seleccionado.setImagenPath(nuevaImagen);
                seleccionado.setEstado(nuevoEstado);
                // Refrescar la tabla para mostrar los cambios
                productosTable.refresh();

                // Limpiar campos
                nombreField.clear();
                categoriaField.clear();
                precioField.clear();
                imagenPathField.clear();
                estadoField.clear();
                mensajeError.setText("Producto actualizado correctamente.");

            } catch (NumberFormatException e) {
                mensajeError.setText("El precio debe ser un número válido.");
            } catch (IllegalArgumentException e) {
                mensajeError.setText(e.getMessage());
            }
        } else {
            mensajeError.setText("Debe seleccionar un producto para actualizar.");
        }
    }
}
