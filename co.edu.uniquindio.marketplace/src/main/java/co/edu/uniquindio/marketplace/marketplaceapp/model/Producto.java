package co.edu.uniquindio.marketplace.marketplaceapp.model;

import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Producto {
    private String nombre;
    private String categoria;
    private double precio;
    private String estado;
    private LocalDateTime fechaPublicacion;
    private Image imagenPath; // Ruta de la imagen
    private List<String> likes;
    private List<Comentario> comentarios;

    // Constructor con imagenPath
    public Producto(String nombre, String categoria, double precio, String estado, Image imagenPath,LocalDateTime fechaPublicacion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
        this.imagenPath = imagenPath; // Inicialización correcta de la ruta de imagen
        this.fechaPublicacion = fechaPublicacion;
        this.likes = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    public Producto(String nombre, String categoria, double precio, String estado, Image imagenPath) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
        this.imagenPath = imagenPath;
    }

    public void agregarLike(String username) {
        if (!likes.contains(username)) {
            likes.add(username);
            System.out.println(username + " dio Me Gusta al producto: " + this.nombre);
        } else {
            System.out.println(username + " ya había dado Me Gusta.");
        }
    }
    public void agregarComentario(Comentario comentario) {
        comentarios.add(comentario);
    }

    // Getters y Setters
    public Image getImagenPath() {
        return imagenPath;
    }

    public void setImagenPath(Image imagenPath) {
        this.imagenPath = imagenPath;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

}
