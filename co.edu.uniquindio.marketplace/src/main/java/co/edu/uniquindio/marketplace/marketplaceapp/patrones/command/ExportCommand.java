package co.edu.uniquindio.marketplace.marketplaceapp.patrones.command;
import co.edu.uniquindio.marketplace.marketplaceapp.services.ExportarServicio;


import java.io.FileWriter;
import java.io.IOException;

public class ExportCommand {
    private final ExportarServicio servicio;

    public ExportCommand(ExportarServicio servicio) {
        this.servicio = servicio;
    }

    public void ejecutar(String ruta, String contenido) {
        servicio.exportar(ruta, contenido);
    }
}
