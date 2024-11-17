package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;

public class ContactosController {

    @FXML
    private TableView<Vendedor> contactosTable;

    @FXML
    private TableColumn<Vendedor, String> nombreColumn;

    @FXML
    private TableColumn<Vendedor, String> usernameColumn;

    @FXML
    private TextField buscarContactoField;

    @FXML
    private Button agregarContactoButton;

    private ObservableList<Vendedor> contactosList = FXCollections.observableArrayList();
    private Vendedor vendedorActual;

    @FXML
    public void initialize() {
        // Configurar columnas
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        contactosTable.setItems(contactosList);
    }

    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        cargarContactos();
    }

    private void cargarContactos() {
        if (vendedorActual != null) {
            contactosList.setAll(vendedorActual.getContactos());
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
        }
    }

    @FXML
    public void handleEliminarContacto() {
        Vendedor seleccionado = contactosTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            vendedorActual.eliminarContacto(seleccionado);
            cargarContactos();
        }
    }

    private Vendedor buscarVendedorPorUsername(String username) {
        // Buscar el vendedor por su nombre de usuario en la lista global
        for (Vendedor vendedor : ModelFactory.getInstance().getRedSocial().getVendedores()) {
            if (vendedor.getUsername().equals(username)) {
                return vendedor;
            }
        }
        return null;
    }
}
