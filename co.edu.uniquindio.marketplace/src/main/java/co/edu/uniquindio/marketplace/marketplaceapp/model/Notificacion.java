package co.edu.uniquindio.marketplace.marketplaceapp.model;

import java.time.LocalDateTime;

public class Notificacion {
    private String tipo;
    private String mensaje;
    private LocalDateTime fecha;

    public Notificacion(String tipo, String mensaje, LocalDateTime fecha) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }
    // Getters y setters
    public String getTipo() { return tipo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFecha() { return fecha; }
}
