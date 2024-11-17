package co.edu.uniquindio.marketplace.marketplaceapp.model.builder;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import javafx.scene.image.Image;

public class ProductoBuilder {
    private String nombre;
    private String categoria;
    private double precio;
    private String estado;
    private Image imagenPath;

    public ProductoBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ProductoBuilder setCategoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public ProductoBuilder setPrecio(double precio) {
        this.precio = precio;
        return this;
    }

    public ProductoBuilder setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public ProductoBuilder setImagenPath(Image imagenPath) {
        this.imagenPath = imagenPath;
        return this;
    }

    public Producto build() {
        Producto producto = new Producto(nombre, categoria, precio, estado, imagenPath);
        producto.setImagenPath(imagenPath); // Asignar la ruta de la imagen al producto
        return producto;
    }
}
