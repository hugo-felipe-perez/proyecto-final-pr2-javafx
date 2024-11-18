package co.edu.uniquindio.marketplace.marketplaceapp.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Solicitud {
    private Vendedor origen;
    private Vendedor destino;
    private String estado;
    private LocalDateTime fecha;
    private String mensaje;

    public Solicitud(Vendedor origen, Vendedor destino, String mensaje) {
        if (origen.equals(destino)) {
            throw new IllegalArgumentException("El origen y el destino no pueden ser iguales.");
        }
        this.origen = origen;
        this.destino = destino;
        this.estado = "Pendiente";
        this.fecha = LocalDateTime.now();
        this.mensaje = mensaje != null ? mensaje : "Sin mensaje.";
    }

    public void cambiarEstado(String nuevoEstado) {
        if (nuevoEstado.equals("Pendiente") || nuevoEstado.equals("Aceptada") || nuevoEstado.equals("Rechazada")) {
            this.estado = nuevoEstado;
        } else {
            throw new IllegalArgumentException("Estado no válido: " + nuevoEstado);
        }
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public Vendedor getOrigen() {
        return origen;
    }

    public Vendedor getDestino() {
        return destino;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "De: " + origen.getNombre() + " | Fecha: " + fecha.format(formatter) + " | Estado: " + estado;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Solicitud solicitud = (Solicitud) obj;
        return origen.equals(solicitud.origen) && destino.equals(solicitud.destino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origen, destino);
    }
}
