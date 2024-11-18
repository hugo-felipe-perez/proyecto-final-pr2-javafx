package co.edu.uniquindio.marketplace.marketplaceapp.services;

import java.io.FileWriter;
import java.io.IOException;

public class ExportarServicio {
    public void exportar(String ruta, String contenido) {
        try (FileWriter writer = new FileWriter(ruta)) {
            writer.write(contenido);
        } catch (IOException e) {
            System.err.println("Error al exportar: " + e.getMessage());
        }
    }
}