package co.edu.uniquindio.marketplace.marketplaceapp.controller;

import co.edu.uniquindio.marketplace.marketplaceapp.patrones.command.ExportCommand;
import co.edu.uniquindio.marketplace.marketplaceapp.patrones.command.ExportarServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EstadisticasController {

    @FXML
    private TextArea estadisticasArea;

    @FXML
    public void initialize() {
        estadisticasArea.setText("Panel de estadísticas:\n- Productos publicados: 50\n- Contactos agregados: 10\n...");
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

            try (FileWriter writer = new FileWriter(file)) {
                writer.write("<Título> Panel de Estadísticas\n");
                writer.write("<Fecha> " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n");
                writer.write("<Usuario> Reporte realizado por: UsuarioActual\n");
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
