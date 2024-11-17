package co.edu.uniquindio.marketplace.marketplaceapp.model.builder;

import co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto.ReporteDTO;

public class ReporteBuilder {
    private String titulo;
    private String fecha;
    private String usuario;
    private String contenido;

    public ReporteBuilder setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public ReporteBuilder setFecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public ReporteBuilder setUsuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public ReporteBuilder setContenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public ReporteDTO build() {
        return new ReporteDTO(titulo, fecha, usuario, contenido);
    }
}
