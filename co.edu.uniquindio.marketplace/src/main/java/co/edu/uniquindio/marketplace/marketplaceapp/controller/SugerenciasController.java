package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;

import java.util.stream.Collectors;

public class SugerenciasController {

    @FXML
    private TableView<Vendedor> sugerenciasTable;

    @FXML
    private TableColumn<Vendedor, String> nombreColumn;

    @FXML
    private TableColumn<Vendedor, String> apellidosColumn;

    private ObservableList<Vendedor> sugerenciasList = FXCollections.observableArrayList();

    private ModelFactory modelFactory = ModelFactory.getInstance();
    private Vendedor vendedorActual;

    @FXML
    public void initialize() {
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        apellidosColumn.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        sugerenciasTable.setItems(sugerenciasList);

        cargarSugerencias();
    }

    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        cargarSugerencias();
    }

    private void cargarSugerencias() {
        if (vendedorActual != null) {
            sugerenciasList.setAll(
                    modelFactory.getRedSocial().getVendedores().stream()
                            .filter(v -> !vendedorActual.getContactos().contains(v) && !v.equals(vendedorActual))
                            .collect(Collectors.toList())
            );
        }
    }

    @FXML
    public void handleAgregarContacto() {
        Vendedor seleccionado = sugerenciasTable.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            vendedorActual.agregarContacto(seleccionado);
            cargarSugerencias();
        }
    }
}
