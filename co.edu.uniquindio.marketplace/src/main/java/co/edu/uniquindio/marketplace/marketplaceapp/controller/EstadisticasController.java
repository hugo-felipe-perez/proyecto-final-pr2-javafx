package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.services.IReporte;
import co.edu.uniquindio.marketplace.marketplaceapp.patrones.decorator.ReporteConEstadisticas;
import co.edu.uniquindio.marketplace.marketplaceapp.patrones.decorator.ReporteConEstilo;
import co.edu.uniquindio.marketplace.marketplaceapp.patrones.decorator.ReporteSimple;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.utils.DataUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EstadisticasController {

    @FXML
    private TextArea estadisticasArea;

    private List<Vendedor> vendedores;
    @FXML
    private ImageView imageEstadisticas;
    @FXML
    public void initialize() {
        // Carga inicial de los vendedores
        try {
            // Configurar la imagen de fondo
            Image background = new Image(getClass().getResource("/images/EstadisticasPanel.png").toExternalForm());
            imageEstadisticas.setImage(background);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
        }
        vendedores = DataUtil.cargarVendedoresIniciales();
        cargarEstadisticasSimple();
    }

    private void cargarEstadisticasSimple() {
        IReporte reporte = new ReporteSimple(vendedores);
        estadisticasArea.setText(reporte.generarReporte());
    }

    private void cargarEstadisticasDetalladas() {
        IReporte reporte = new ReporteConEstadisticas(new ReporteSimple(vendedores), vendedores);
        estadisticasArea.setText(reporte.generarReporte());
    }

    private void cargarEstadisticasConEstilo() {
        IReporte reporte = new ReporteConEstilo(new ReporteConEstadisticas(new ReporteSimple(vendedores), vendedores));
        estadisticasArea.setText(reporte.generarReporte());
    }

    @FXML
    public void handleReporteSimple() {
        cargarEstadisticasSimple();
    }

    @FXML
    public void handleReporteDetallado() {
        cargarEstadisticasDetalladas();
    }

    @FXML
    public void handleReporteConEstilo() {
        cargarEstadisticasConEstilo();
    }

    @FXML
    public void handleExportar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("<Título> Reporte de Estadísticas\n");
                writer.write("<Fecha> " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n");
                writer.write("<Usuario> Reporte realizado por: Administrador\n");
                writer.write("\nInformación del reporte:\n");
                writer.write(estadisticasArea.getText());
                writer.write("\n--------------------------------------\n");

                mostrarAlerta("Éxito", "Reporte exportado correctamente a " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo exportar el reporte.", Alert.AlertType.ERROR);
            }
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
