package co.edu.uniquindio.marketplace.marketplaceapp.model;

import java.time.LocalDateTime;

public class Comentario {
    private String texto;
    private String autor; // Nombre del contacto que hizo el comentario
    private LocalDateTime fechaComentario;

    public Comentario(String texto, String autor) {
        this.texto = texto;
        this.autor = autor;
        this.fechaComentario = LocalDateTime.now();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDateTime getFechaComentario() {
        return fechaComentario;
    }
}