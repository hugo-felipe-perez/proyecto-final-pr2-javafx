package co.edu.uniquindio.marketplace.marketplaceapp.mapping.dto;

import java.time.LocalDateTime;

public class ProductoDTO {
    private String nombre;
    private String categoria;
    private double precio;
    private String estado;
    private LocalDateTime fechaPublicacion;

    public ProductoDTO(String nombre, String categoria, double precio, String estado, LocalDateTime fechaPublicacion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.estado = estado;
        this.fechaPublicacion = fechaPublicacion;
    }

    // Getters y Setters
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
}
