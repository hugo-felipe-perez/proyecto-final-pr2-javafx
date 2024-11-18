package co.edu.uniquindio.marketplace.marketplaceapp.patrones.templatemethod;

public class ExportadorTexto extends ExportadorTemplate {
    @Override
    protected void prepararArchivo(String ruta) {
        System.out.println("Preparando archivo de texto...");
    }

    @Override
    protected void escribirContenido(String contenido) {
        System.out.println("Escribiendo contenido en archivo de texto...");
    }

    @Override
    protected void cerrarArchivo() {
        System.out.println("Cerrando archivo...");
    }
}