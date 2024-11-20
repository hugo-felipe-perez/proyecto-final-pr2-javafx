package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Comentario;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MuroController {

    @FXML
    private TableView<Producto> muroTable;

    @FXML
    private TableColumn<Producto, ImageView> imagenColumn;

    @FXML
    private TableColumn<Producto, String> nombreColumn;

    @FXML
    private TableColumn<Producto, String> categoriaColumn;

    @FXML
    private TableColumn<Producto, String> estadoColumn;

    @FXML
    private TableColumn<Producto, String> fechaPublicacionColumn;

    @FXML
    private TableColumn<Producto, Integer> likesColumn;

    @FXML
    private TableColumn<Producto, Integer> comentariosColumn;

    @FXML
    private TextArea estadisticasArea;

    @FXML
    private TextField searchField;

    private ObservableList<Producto> productosMuro = FXCollections.observableArrayList();
    private ObservableList<Vendedor> contactosList = FXCollections.observableArrayList();

    private Vendedor vendedorActual;

    @FXML
    public void initialize() {
        configurarColumnas();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> buscarProductos(newValue));

    }

    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        cargarMuro();
        cargarContactos();
        cargarEstadisticas();
    }

    private void configurarColumnas() {
        imagenColumn.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView(cellData.getValue().getImagenPath());
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            return new ReadOnlyObjectWrapper<>(imageView);
        });


        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        fechaPublicacionColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return new ReadOnlyObjectWrapper<>(cellData.getValue().getFechaPublicacion().format(formatter));
        });

        likesColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLikes().size()));
        comentariosColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getComentarios().size()));

        muroTable.setItems(productosMuro);
    }

    private void cargarMuro() {
        productosMuro.clear();
        if (vendedorActual != null) {
            productosMuro.setAll(vendedorActual.getProductos());
            vendedorActual.getContactos().forEach(contacto -> productosMuro.addAll(contacto.getProductos()));
            productosMuro.sort((p1, p2) -> p2.getFechaPublicacion().compareTo(p1.getFechaPublicacion()));
        }
        muroTable.setItems(productosMuro); // Asigna los productos actualizados
    }

    private void cargarContactos() {
        contactosList.clear();
        if (vendedorActual != null) {
            contactosList.addAll(vendedorActual.getContactos());
        }
    }

    private void cargarEstadisticas() {
        StringBuilder estadisticas = new StringBuilder("Estadísticas:\n");
        estadisticas.append("- Total de productos publicados: ").append(productosMuro.size()).append("\n");
        estadisticas.append("- Total de contactos: ").append(contactosList.size()).append("\n");
        estadisticasArea.setText(estadisticas.toString());
    }

    @FXML
    public void handleDarMeGusta() {
        Producto seleccionado = muroTable.getSelectionModel().getSelectedItem();
        System.out.println("Intentando dar Me Gusta...");
        if (seleccionado != null) {
            vendedorActual = ModelFactory.getInstance().getUsuarioActual();
            System.out.println("Producto seleccionado: " + seleccionado.getNombre());
            if (vendedorActual != null) {
                System.out.println("Usuario actual: " + vendedorActual.getUsername());
                seleccionado.agregarLike(vendedorActual.getUsername());
                Vendedor propietario = obtenerVendedorPorProducto(seleccionado);
                if (propietario != null && !propietario.equals(vendedorActual)) {
                    enviarNotificacion(propietario, "Me Gusta",
                            vendedorActual.getNombre() + " le dio Me Gusta a tu producto: " + seleccionado.getNombre());
                }
                cargarMuro();
            } else {
                mostrarAlerta("Error", "El usuario actual no está definido.", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Advertencia", "Debes seleccionar un producto para dar Me Gusta", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void handleAgregarComentario() {
        Producto seleccionado = muroTable.getSelectionModel().getSelectedItem();
        System.out.println("Intentando agregar comentario...");
        if (seleccionado != null) {
            System.out.println("Producto seleccionado: " + seleccionado.getNombre());
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Agregar Comentario");
            dialog.setHeaderText("Comentario para: " + seleccionado.getNombre());
            dialog.setContentText("Escribe tu comentario:");

            Optional<String> resultado = dialog.showAndWait();
            resultado.ifPresent(texto -> {
                if (vendedorActual != null) {
                    System.out.println("Usuario actual: " + vendedorActual.getUsername());
                    Comentario comentario = new Comentario(texto, vendedorActual.getNombre());
                    seleccionado.agregarComentario(comentario);
                    Vendedor propietario = obtenerVendedorPorProducto(seleccionado);
                    if (propietario != null && !propietario.equals(vendedorActual)) {
                        enviarNotificacion(propietario, "Comentario",
                                vendedorActual.getNombre() + " comentó en tu producto: " + seleccionado.getNombre());
                    }
                    cargarMuro();
                } else {
                    mostrarAlerta("Error", "El usuario actual no está definido.", Alert.AlertType.ERROR);
                }
            });
        } else {
            mostrarAlerta("Advertencia", "Debes seleccionar un producto para comentar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    public void handleVerLikes(ActionEvent actionEvent) {
        Producto seleccionado = muroTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lista de Likes");
            alert.setHeaderText("Usuarios que dieron Like:");
            alert.setContentText(String.join(", ", seleccionado.getLikes()));
            alert.showAndWait();
        } else {
            mostrarAlerta("Advertencia", "Debes seleccionar un producto para ver los Likes", Alert.AlertType.WARNING);
        }
    }

    private Vendedor obtenerVendedorPorProducto(Producto producto) {
        System.out.println("Buscando propietario del producto...");
        for (Vendedor contacto : vendedorActual.getContactos()) {
            if (contacto.getProductos().contains(producto)) {
                return contacto;
            }
        }
        if (vendedorActual.getProductos().contains(producto)) {
            return vendedorActual;
        }
        return null;
    }

    private void enviarNotificacion(Vendedor vendedor, String tipo, String mensaje) {
        System.out.println("Enviando notificación a: " + vendedor.getUsername());
        System.out.println("Tipo: " + tipo + " - Mensaje: " + mensaje);
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    public void handleVerComentarios(ActionEvent actionEvent) {
        Producto seleccionado = muroTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (seleccionado.getComentarios().isEmpty()) {
                mostrarAlerta("Información", "Este producto no tiene comentarios aún.", Alert.AlertType.INFORMATION);
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Comentarios.fxml"));
                    AnchorPane pane = loader.load();

                    ComentariosController controller = loader.getController();
                    controller.setComentarios(seleccionado.getComentarios());

                    Stage stage = new Stage();
                    stage.setTitle("Comentarios para " + seleccionado.getNombre());
                    stage.setScene(new Scene(pane));
                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlerta("Error", "No se pudo cargar la vista de comentarios.", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Advertencia", "Debes seleccionar un producto para ver los comentarios.", Alert.AlertType.WARNING);
        }
    }
    @FXML
    public void handleVerSolicitudes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Solicitudes.fxml"));
            AnchorPane pane = loader.load();

            SolicitudesController controller = loader.getController();
            controller.setVendedorActual(vendedorActual); // Pasa el vendedor actual

            Stage stage = new Stage();
            stage.setTitle("Solicitudes Recibidas");
            stage.setScene(new Scene(pane));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void buscarProductos(String query) {
        if (query == null || query.isEmpty()) {
            muroTable.setItems(productosMuro); // Muestra todos los productos si no hay texto
        } else {
            ObservableList<Producto> productosFiltrados = FXCollections.observableArrayList();
            for (Producto producto : productosMuro) {
                if (producto.getNombre().toLowerCase().contains(query.toLowerCase()) ||
                        producto.getCategoria().toLowerCase().contains(query.toLowerCase())) {
                    productosFiltrados.add(producto);
                }
            }
            muroTable.setItems(productosFiltrados);
        }
    }
    @FXML
    public void handleVerTablero() {
        System.out.println("Entrando a handleVerTablero...");
        try {
            System.out.println("Intentando cargar Tablero.fxml...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Tablero.fxml"));
            BorderPane pane = loader.load();
            System.out.println("Archivo FXML cargado exitosamente");

            TableroController controller = loader.getController(); // Si realmente es TableroController
            System.out.println("Controlador cargado correctamente");

            if (controller != null) {
                controller.setVendedorActual(vendedorActual);
            } else {
                System.err.println("Error: Controlador no inicializado.");
            }

            Stage stage = new Stage();
            stage.setTitle("TABLERO DE CONTROL");
            stage.setScene(new Scene(pane));
            stage.show();
            System.out.println("Ventana de Tablero mostrada exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleActualizarTabla(ActionEvent actionEvent) {
        System.out.println("Actualizando la tabla de productos...");
        cargarMuro(); // Recarga los productos del muro
        muroTable.refresh(); // Refresca la tabla para reflejar los cambios
        System.out.println("Tabla de productos actualizada.");
    }
}
