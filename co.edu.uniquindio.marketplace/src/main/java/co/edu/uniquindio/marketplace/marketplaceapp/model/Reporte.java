package co.edu.uniquindio.marketplace.marketplaceapp.model;

public  class Reporte {
    private final String titulo;
    private final String contenido;

    public Reporte(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public String getContenido() {
        return "Título: " + titulo + "\n" + contenido;
    }
}