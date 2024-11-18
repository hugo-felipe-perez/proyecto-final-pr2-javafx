package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ContactosController {

    @FXML
    private TableView<Vendedor> contactosTable;

    @FXML
    private TableColumn<Vendedor, String> nombreColumn;

    @FXML
    private TableColumn<Vendedor, String> usernameColumn;

    @FXML
    private TextField buscarContactoField;
    private ObservableList<Vendedor> contactosList = FXCollections.observableArrayList();
    private Vendedor vendedorActual;

    @FXML
    public void initialize() {
        nombreColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNombre()));
        usernameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUsername()));
        contactosTable.setItems(contactosList);
        ModelFactory.getInstance().setContactosController(this);
        cargarContactos();
    }

    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        cargarContactos();
    }

    public void cargarContactos() {
        contactosTable.getItems().clear();
        if (vendedorActual != null) {
            contactosTable.getItems().addAll(vendedorActual.getContactos());
        }
    }

    @FXML
    public void handleAgregarContacto() {
        String username = buscarContactoField.getText();
        Vendedor nuevoContacto = buscarVendedorPorUsername(username);

        if (nuevoContacto != null && !vendedorActual.getContactos().contains(nuevoContacto)) {
            vendedorActual.agregarContacto(nuevoContacto);
            cargarContactos();
            buscarContactoField.clear();
            mostrarAlerta("Éxito", "Contacto agregado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("Error", "El contacto no existe o ya está en tu lista.", Alert.AlertType.ERROR);
        }
    }

    private Vendedor buscarVendedorPorUsername(String username) {
        return ModelFactory.getInstance().getRedSocial().getVendedores().stream()
                .filter(v -> v.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

}
