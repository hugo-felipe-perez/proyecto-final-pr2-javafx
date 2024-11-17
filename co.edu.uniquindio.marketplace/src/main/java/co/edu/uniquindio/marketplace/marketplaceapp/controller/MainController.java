package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.factory.ModelFactory;
import co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto.ReporteDTO;
import co.edu.uniquindio.marketplace.marketplaceapp.service.EstadisticasService;
import co.edu.uniquindio.marketplace.marketplaceapp.service.ExportarService;

import java.io.IOException;
import java.time.LocalDateTime;

public class MainController {

    @FXML
    private TabPane tabPane;

    @FXML
    private TextArea estadisticasArea;

    private ModelFactory modelFactory = ModelFactory.getInstance();
    private EstadisticasService estadisticasService = new EstadisticasService();
    private ExportarService exportarService = new ExportarService();

    @FXML
    public void initialize() throws IOException {
        cargarEstadisticas();
        generarPestanasVendedores();
    }

    private void cargarEstadisticas() {
        StringBuilder estadisticas = new StringBuilder("Estadísticas:\n");
        for (Vendedor vendedor : modelFactory.getRedSocial().getVendedores()) {
            estadisticas.append("Vendedor: ").append(vendedor.getNombre()).append("\n");
            estadisticas.append("- Contactos: ").append(estadisticasService.contarContactosPorVendedor(vendedor)).append("\n");
            estadisticas.append("- Productos: ").append(estadisticasService.contarProductosPorVendedor(vendedor)).append("\n");
        }
        estadisticasArea.setText(estadisticas.toString());
    }

    private void generarPestanasVendedores() throws IOException {
        for (Vendedor vendedor : modelFactory.getRedSocial().getVendedores()) {
            Tab tab = new Tab(vendedor.getNombre());
            tab.setContent(crearVistaVendedor(vendedor));
            tabPane.getTabs().add(tab);
        }
    }
    private TabPane crearVistaVendedor(Vendedor vendedor) throws IOException {
        TabPane vendedorTabs = new TabPane();

        Tab muroTab = new Tab("Muro");
        Tab contactosTab = new Tab("Contactos");
        Tab productosTab = new Tab("Productos");
        Tab sugerenciasTab = new Tab("Sugerencias");

        // Crear un FXMLLoader para cada archivo FXML y cargar su contenido
        FXMLLoader muroLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Muro.fxml"));
        muroTab.setContent(muroLoader.load());
        MuroController muroController = muroLoader.getController(); // Obtener el controlador
        muroController.setVendedorActual(vendedor);

        FXMLLoader contactosLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Contactos.fxml"));
        contactosTab.setContent(contactosLoader.load());
        ContactosController contactosController = contactosLoader.getController(); // Obtener el controlador
        contactosController.setVendedorActual(vendedor);

        FXMLLoader productosLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Productos.fxml"));
        productosTab.setContent(productosLoader.load());
        ProductosController productosController = productosLoader.getController(); // Obtener el controlador
        productosController.setVendedorActual(vendedor);

        FXMLLoader sugerenciasLoader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/marketplace/marketplaceapp/Sugerencias.fxml"));
        sugerenciasTab.setContent(sugerenciasLoader.load());
        SugerenciasController sugerenciasController = sugerenciasLoader.getController(); // Obtener el controlador
        sugerenciasController.setVendedorActual(vendedor);

        // Agregar todas las pestañas al TabPane
        vendedorTabs.getTabs().addAll(muroTab, contactosTab, productosTab, sugerenciasTab);
        return vendedorTabs;
    }
    @FXML
    public void handleExportar() {
        ReporteDTO reporte = new ReporteDTO(
                "Reporte de Estadísticas de la Red",
                LocalDateTime.now().toString(),
                "Administrador",
                estadisticasArea.getText()
        );
        exportarService.exportarReporte(reporte, "estadisticas_red.txt");
    }

}