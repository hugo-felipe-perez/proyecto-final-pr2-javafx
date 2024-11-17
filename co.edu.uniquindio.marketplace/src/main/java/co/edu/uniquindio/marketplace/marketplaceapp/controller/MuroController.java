package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Comentario;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
    private TableColumn<Producto, Integer> likesColumn;

    @FXML
    private TableColumn<Producto, Integer> comentariosColumn;

    private ObservableList<Producto> productosMuro = FXCollections.observableArrayList();

    private ModelFactory modelFactory = ModelFactory.getInstance();
    private Vendedor vendedorActual;

    @FXML
    public void initialize() {
        // Configuración de columnas
         imagenColumn.setCellValueFactory(cellData -> {
            ImageView imageView = new ImageView();
            imageView.setFitHeight(50); // Altura de las imágenes
            imageView.setFitWidth(50);  // Ancho de las imágenes

            // Verificar si el producto tiene una imagen válida
            Image image = cellData.getValue().getImagenPath(); // Obtener el objeto Image directamente
            if (image != null) {
                imageView.setImage(image);
            } else {
                System.err.println("La imagen es nula para el producto: " + cellData.getValue().getNombre());
            }
            return new ReadOnlyObjectWrapper<>(imageView);
        });

        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        likesColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLikes().size()));
        comentariosColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getComentarios().size()));

        muroTable.setItems(productosMuro);
    }

    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        cargarMuro();
    }

    private void cargarMuro() {
        productosMuro.clear();
        if (vendedorActual != null) {
            productosMuro.addAll(vendedorActual.getProductos());
            vendedorActual.getContactos().forEach(contacto -> productosMuro.addAll(contacto.getProductos()));
        }
        productosMuro.sort((p1, p2) -> p2.getFechaPublicacion().compareTo(p1.getFechaPublicacion()));
    }

    @FXML
    public void handleDarMeGusta() {
        Producto seleccionado = muroTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.agregarLike(vendedorActual.getNombre());

            // Buscar al propietario del producto y enviarle una notificación
            Vendedor propietario = modelFactory.getRedSocial().buscarVendedorPorProducto(seleccionado);
            if (propietario != null && !propietario.equals(vendedorActual)) {
                enviarNotificacion(propietario, "Me Gusta", vendedorActual.getNombre() + " le dio me gusta a tu producto: " + seleccionado.getNombre());
            }

            cargarMuro();
        } else {
            mostrarAlerta("Advertencia", "Debe seleccionar un producto para dar Me Gusta", Alert.AlertType.WARNING);
        }
    }
    @FXML
    public void handleAgregarComentario() {
        Producto seleccionado = muroTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Agregar Comentario");
            dialog.setHeaderText("Comentario para: " + seleccionado.getNombre());
            dialog.setContentText("Escribe tu comentario:");

            Optional<String> resultado = dialog.showAndWait();
            resultado.ifPresent(texto -> {
                Comentario comentario = new Comentario(texto, vendedorActual.getNombre());
                seleccionado.agregarComentario(comentario);

                // Buscar al propietario del producto y enviarle una notificación
                Vendedor propietario = modelFactory.getRedSocial().buscarVendedorPorProducto(seleccionado);
                if (propietario != null && !propietario.equals(vendedorActual)) {
                    enviarNotificacion(propietario, "Comentario", vendedorActual.getNombre() + " comentó en tu producto: " + seleccionado.getNombre());
                }

                cargarMuro();
            });
        } else {
            mostrarAlerta("Advertencia", "Debe seleccionar un producto para agregar un comentario", Alert.AlertType.WARNING);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    public void setVendedor(Vendedor vendedor) {
        productosMuro.setAll(vendedor.getProductos());
        muroTable.setItems(productosMuro);
    }
    private void enviarNotificacion(Vendedor vendedor, String tipo, String mensaje) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Notificaciones.fxml"));
            AnchorPane pane = loader.load();

            // Obtener el controlador de la ventana de notificaciones
            NotificacionesController controller = loader.getController();

            // Agregar la notificación al controlador
            controller.agregarNotificacion(tipo, mensaje);

            // Crear y mostrar la nueva ventana
            Stage stage = new Stage();
            stage.setTitle("Notificaciones para " + vendedor.getNombre());
            stage.setScene(new Scene(pane));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
