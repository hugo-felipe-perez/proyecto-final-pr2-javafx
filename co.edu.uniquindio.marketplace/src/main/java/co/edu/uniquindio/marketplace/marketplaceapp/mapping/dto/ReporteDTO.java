package co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto;

public class ReporteDTO {
    private String titulo;
    private String fecha;
    private String usuario;
    private String contenido;

    public ReporteDTO(String titulo, String fecha, String usuario, String contenido) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.usuario = usuario;
        this.contenido = contenido;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContenido() {
        return contenido;
    }
}
