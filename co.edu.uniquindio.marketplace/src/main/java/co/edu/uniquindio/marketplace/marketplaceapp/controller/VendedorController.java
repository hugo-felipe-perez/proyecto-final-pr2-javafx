package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VendedorController {

    @FXML
    private TabPane tabPaneVendedor;

    private Vendedor vendedorActual;

    private final ModelFactory modelFactory = ModelFactory.getInstance();

    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        inicializarPestanas();
        cargarPestanasOtrosVendedores();
    }

    private void inicializarPestanas() {
        try {
            // Pestaña de Muro
            Tab muroTab = new Tab("Muro");
            FXMLLoader muroLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Muro.fxml"));
            muroTab.setContent(muroLoader.load());
            MuroController muroController = muroLoader.getController();
            muroController.setVendedorActual(vendedorActual);

            // Pestaña de Contactos
            Tab contactosTab = new Tab("Contactos");
            FXMLLoader contactosLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Contactos.fxml"));
            contactosTab.setContent(contactosLoader.load());
            ContactosController contactosController = contactosLoader.getController();
            contactosController.setVendedorActual(vendedorActual);

            // Pestaña de Productos
            Tab productosTab = new Tab("Productos");
            FXMLLoader productosLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Productos.fxml"));
            productosTab.setContent(productosLoader.load());
            ProductosController productosController = productosLoader.getController();
            productosController.setVendedorActual(vendedorActual);

            // Pestaña de Sugerencias
            Tab sugerenciasTab = new Tab("Sugerencias");
            FXMLLoader sugerenciasLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Sugerencias.fxml"));
            sugerenciasTab.setContent(sugerenciasLoader.load());
            SugerenciasController sugerenciasController = sugerenciasLoader.getController();
            sugerenciasController.setVendedorActual(vendedorActual);

            // Agregar las pestañas iniciales al TabPane
            tabPaneVendedor.getTabs().addAll(muroTab, contactosTab, productosTab, sugerenciasTab);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarPestanasOtrosVendedores() {
        try {
            // Iterar sobre los vendedores diferentes al vendedor actual
            for (Vendedor otroVendedor : modelFactory.getRedSocial().getVendedores()) {
                if (!otroVendedor.equals(vendedorActual)) {
                    // Crear una pestaña para el muro del otro vendedor
                    Tab muroOtroVendedorTab = new Tab("Muro de " + otroVendedor.getNombre());

                    FXMLLoader muroOtroLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Muro.fxml"));
                    muroOtroVendedorTab.setContent(muroOtroLoader.load());

                    // Configurar el controlador del muro del otro vendedor
                    MuroController muroOtroController = muroOtroLoader.getController();
                    muroOtroController.setVendedorActual(otroVendedor);

                    // Agregar la pestaña del otro vendedor al TabPane
                    tabPaneVendedor.getTabs().add(muroOtroVendedorTab);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
