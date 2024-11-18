package co.edu.uniquindio.marketplace.marketplaceapp.model.builder;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Reporte;

public class ReporteBuilder {
    private String titulo;
    private String contenido;

    public ReporteBuilder setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public ReporteBuilder setContenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public Reporte build() {
        return new Reporte(titulo, contenido);
    }
}