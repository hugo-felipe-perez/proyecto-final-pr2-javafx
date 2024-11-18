package co.edu.uniquindio.marketplace.marketplaceapp.patrones.templatemethod;

public abstract class ExportadorTemplate {
    public final void exportar(String ruta, String contenido) {
        prepararArchivo(ruta);
        escribirContenido(contenido);
        cerrarArchivo();
    }

    protected abstract void prepararArchivo(String ruta);

    protected abstract void escribirContenido(String contenido);

    protected abstract void cerrarArchivo();
}