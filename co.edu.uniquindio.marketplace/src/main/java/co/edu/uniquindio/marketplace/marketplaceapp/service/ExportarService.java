package co.edu.uniquindio.marketplace.marketplaceapp.service;

import co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto.ReporteDTO;

import java.io.FileWriter;
import java.io.IOException;

public class ExportarService {

    public void exportarReporte(ReporteDTO reporte, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write("Título: " + reporte.getTitulo() + "\n");
            writer.write("Fecha: " + reporte.getFecha() + "\n");
            writer.write("Usuario: " + reporte.getUsuario() + "\n");
            writer.write("\nContenido:\n" + reporte.getContenido());
            writer.write("\n-------------------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
