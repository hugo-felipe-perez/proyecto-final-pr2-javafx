package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.patrones.singleton.EstadisticasSingleton;
import co.edu.uniquindio.marketplace.marketplaceapp.patrones.builder.ReporteBuilder;
import co.edu.uniquindio.marketplace.marketplaceapp.patrones.command.ExportCommand;
import co.edu.uniquindio.marketplace.marketplaceapp.patrones.command.ExportarServicio;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TableroController {

    @FXML
    private BarChart<String, Number> topProductosChart;

    @FXML
    private TextField fechaInicioField;

    @FXML
    private TextField fechaFinField;

    @FXML
    private TextArea indicadoresArea;

    private EstadisticasSingleton estadisticasSingleton = EstadisticasSingleton.getInstance();

    @FXML
    public void initialize() {
        cargarTopProductos();
        mostrarIndicadores();
    }

    private void cargarTopProductos() {
        topProductosChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top Productos");

        // Simular datos (deberían venir de la lógica real)
        series.getData().add(new XYChart.Data<>("Producto A", 50));
        series.getData().add(new XYChart.Data<>("Producto B", 30));
        series.getData().add(new XYChart.Data<>("Producto C", 20));

        topProductosChart.getData().add(series);
    }

    private void mostrarIndicadores() {
        StringBuilder indicadores = new StringBuilder();
        indicadores.append("Indicadores del Tablero:\n");
        indicadores.append("- Total de mensajes enviados: 120\n");
        indicadores.append("- Total de productos publicados: 45\n");
        indicadores.append("- Total de contactos: 30\n");
        indicadoresArea.setText(indicadores.toString());
    }

    @FXML
    public void handleFiltrarEstadisticas() {
        try {
            LocalDate fechaInicio = LocalDate.parse(fechaInicioField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate fechaFin = LocalDate.parse(fechaFinField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            // Lógica para filtrar estadísticas
            cargarTopProductos(); // Simulación
        } catch (Exception e) {
            mostrarAlerta("Error", "Por favor ingresa fechas válidas (dd-MM-yyyy).", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleExportar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de Texto", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            ExportarServicio exportarServicio = new ExportarServicio();
            ExportCommand command = new ExportCommand(exportarServicio);

            String contenidoReporte = new ReporteBuilder()
                    .setTitulo("Reporte de Estadísticas del Tablero")
                    .setContenido(indicadoresArea.getText())
                    .build()
                    .getContenido();

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("<Título> " + "Reporte de Estadísticas\n");
                writer.write("<Fecha> " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n");
                writer.write("<Usuario> Reporte realizado por: UsuarioActual\n");
                writer.write("\nInformación del reporte:\n");
                writer.write(contenidoReporte);
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