package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import co.edu.uniquindio.marketplace.marketplaceapp.service.EstadisticasService;
import co.edu.uniquindio.marketplace.marketplaceapp.service.ExportarService;
import co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto.ReporteDTO;

import java.time.LocalDateTime;

public class EstadisticasController {

    @FXML
    private TextArea estadisticasArea;

    private EstadisticasService estadisticasService = new EstadisticasService();
    private ExportarService exportarService = new ExportarService();

    @FXML
    public void initialize() {
        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
        // Aquí iría la lógica para generar las estadísticas
        estadisticasArea.setText("Estadísticas:\n- Cantidad de productos publicados...\n...");
    }

    @FXML
    public void handleExportar() {
        ReporteDTO reporte = new ReporteDTO(
                "Reporte de Estadísticas",
                LocalDateTime.now().toString(),
                "Administrador",
                estadisticasArea.getText()
        );
        exportarService.exportarReporte(reporte, "estadisticas_reporte.txt");
    }
}
