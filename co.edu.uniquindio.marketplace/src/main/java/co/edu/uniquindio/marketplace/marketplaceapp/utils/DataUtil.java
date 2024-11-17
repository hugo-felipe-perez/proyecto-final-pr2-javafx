package co.edu.uniquindio.marketplace.marketplaceapp.utils;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.enums.Rol;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataUtil {

    public static List<Vendedor> cargarVendedoresIniciales() {
        List<Vendedor> vendedores = new ArrayList<>();

        // Crear vendedores con datos quemados
        Vendedor vendedor1 = new Vendedor("Juan", "Pérez", "123456", "Calle 1", "admin", "1234", Rol.ADMINISTRADOR);
        Vendedor vendedor2 = new Vendedor("Ana", "Gómez", "654321", "Calle 2", "anag", "456", Rol.VENDEDOR);
        Vendedor vendedor3 = new Vendedor("Luis", "Martínez", "789012", "Calle 3", "luism", "password789", Rol.VENDEDOR);

        // Agregar productos al vendedor2
        vendedor2.agregarProducto(new Producto(
                "Teléfono",
                "Electrónica",
                800.0,
                "Vendido",
                new Image(Objects.requireNonNull(DataUtil.class.getResource("/images/telefono.jpg")).toExternalForm()) // Ruta del recurso empaquetado
        ));
        vendedor2.agregarProducto(new Producto(
                "Tablet",
                "Electrónica",
                400.0,
                "Publicado",
                new Image("https://via.placeholder.com/150") // Ruta externa
        ));

        // Agregar productos al vendedor3
        vendedor3.agregarProducto(new Producto(
                "Monitor",
                "Electrónica",
                200.0,
                "Publicado",
                new Image("https://via.placeholder.com/150") // Ruta externa
        ));
        vendedor3.agregarProducto(new Producto(
                "Teclado",
                "Accesorios",
                30.0,
                "Cancelado",
                new Image("https://via.placeholder.com/150") // Ruta externa
        ));

        // Agregar los vendedores a la lista
        vendedores.add(vendedor1);
        vendedores.add(vendedor2);
        vendedores.add(vendedor3);

        return vendedores;
    }
}
