package co.edu.uniquindio.marketplace.marketplaceapp.model;

import java.time.LocalDateTime;

public class Mensaje {
    private Vendedor remitente;
    private Vendedor destinatario;
    private String contenido;
    private LocalDateTime fecha;

    public Mensaje(Vendedor remitente, Vendedor destinatario, String contenido) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.fecha = LocalDateTime.now();
    }

    public Vendedor getRemitente() {
        return remitente;
    }

    public Vendedor getDestinatario() {
        return destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "De: " + remitente.getNombre() + "\nPara: " + destinatario.getNombre() +
                "\nFecha: " + fecha + "\nMensaje: " + contenido;
    }
}
