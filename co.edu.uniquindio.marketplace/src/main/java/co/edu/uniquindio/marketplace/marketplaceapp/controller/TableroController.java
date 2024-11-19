package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.patrones.singleton.EstadisticasSingleton;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.model.builder.ReporteBuilder;
import co.edu.uniquindio.marketplace.marketplaceapp.patrones.command.ExportCommand;
import co.edu.uniquindio.marketplace.marketplaceapp.services.ExportarServicio;
import co.edu.uniquindio.marketplace.marketplaceapp.utils.DataUtil;
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
import java.util.List;

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
    
        private Vendedor vendedorActual;
    
        @FXML
        public void initialize() {
            cargarTopProductos();
            mostrarIndicadores();
        }

    private void cargarTopProductos() {
        topProductosChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top Productos");

        // Obtener el Top 10 de productos
        List<Producto> topProductos = estadisticasSingleton.obtenerTopProductosConMasLikes();

        // Agregar los productos al gráfico
        for (Producto producto : topProductos) {
            series.getData().add(new XYChart.Data<>(producto.getNombre(), producto.getLikes().size()));
        }

        topProductosChart.getData().add(series);
    }
        private void mostrarIndicadores() {
        StringBuilder indicadores = new StringBuilder("Indicadores del Tablero:\n");

        int totalProductos = estadisticasSingleton.obtenerTotalProductosPublicados();
        int totalContactos = DataUtil.cargarVendedoresIniciales().stream()
                .mapToInt(vendedor -> estadisticasSingleton.obtenerCantidadDeContactosPorVendedor(vendedor))
                .sum();

        indicadores.append("- Total de mensajes enviados: ").append(estadisticasSingleton.obtenerTotalMensajesEnviados()).append("\n");
        indicadores.append("- Total de productos publicados: ").append(totalProductos).append("\n");
        indicadores.append("- Total de contactos: ").append(totalContactos).append("\n");

        indicadoresArea.setText(indicadores.toString());
    }

    @FXML
    public void handleFiltrarEstadisticas() {
        try {
            LocalDate fechaInicio = LocalDate.parse(fechaInicioField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            LocalDate fechaFin = LocalDate.parse(fechaFinField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            // Filtrar productos entre fechas
            List<Producto> productosFiltrados = estadisticasSingleton.obtenerProductosPublicadosEntreFechas(fechaInicio, fechaFin);

            topProductosChart.getData().clear();
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Productos Filtrados");

            for (Producto producto : productosFiltrados) {
                series.getData().add(new XYChart.Data<>(producto.getNombre(), producto.getLikes().size()));
            }

            topProductosChart.getData().add(series);
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
                    writer.write("<Usuario> Reporte realizado por: " + (vendedorActual != null ? vendedorActual.getNombre() : "Desconocido") + "\n\n");
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

    public void setVendedorActual(Vendedor vendedor) {
        this.vendedorActual = vendedor;
        cargarTopProductos();
        mostrarIndicadores();
    }
}