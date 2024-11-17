package co.edu.uniquindio.marketplace.marketplaceapp.utils;

import co.edu.uniquindio.marketplace.marketplaceapp.enums.Rol;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {

    public static List<Vendedor> cargarVendedoresIniciales() {
        List<Vendedor> vendedores = new ArrayList<>();

        // Crear vendedores con datos quemados
        Vendedor vendedor1 = new Vendedor("Juan", "Pérez", "123456", "Calle 1", "admin", "1234",Rol.ADMINISTRADOR);
        Vendedor vendedor2 = new Vendedor("Ana", "Gómez", "654321", "Calle 2", "anag", "456", Rol.VENDEDOR);
        Vendedor vendedor3 = new Vendedor("Luis", "Martínez", "789012", "Calle 3", "luism", "password789", Rol.VENDEDOR);

        vendedor2.agregarProducto(new Producto("Teléfono", "Electrónica", 800.0, "Vendido", "File:C:/imagenes/telefono.jpg"));
        vendedor2.agregarProducto(new Producto("Tablet", "Electrónica", 400.0, "Publicado", "https://via.placeholder.com/150"));

        vendedor3.agregarProducto(new Producto("Monitor", "Electrónica", 200.0, "Publicado", "https://via.placeholder.com/150"));
        vendedor3.agregarProducto(new Producto("Teclado", "Accesorios", 30.0, "Cancelado", "https://via.placeholder.com/150"));
        // Agregar los vendedores a la lista
        vendedores.add(vendedor1);
        vendedores.add(vendedor2);
        vendedores.add(vendedor3);

        return vendedores;
    }
}
