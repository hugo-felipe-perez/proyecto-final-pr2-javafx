package co.edu.uniquindio.marketplace.marketplaceapp.utils;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Comentario;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.enums.Rol;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataUtil {

    public static List<Vendedor> cargarVendedoresIniciales() {
        List<Vendedor> vendedores = new ArrayList<>();

        // Administrador
        Vendedor maestro = new Vendedor("Admin", "Administrador",
                "000000", "0000000", "admin", "1234", Rol.ADMINISTRADOR);

        // Vendedores
        Vendedor vendedor2 = new Vendedor("Ana", "Gómez", "654321", "Calle 2", "anag", "456", Rol.VENDEDOR);
        Vendedor vendedor3 = new Vendedor("Luis", "Martínez", "789012", "Calle 3", "luism", "789", Rol.VENDEDOR);
        Vendedor vendedor4 = new Vendedor("Carlos", "Rodríguez", "456789", "Calle 4", "carlosr", "123", Rol.VENDEDOR);
        Vendedor vendedor5 = new Vendedor("María", "Pérez", "987654", "Calle 5", "mariap", "321", Rol.VENDEDOR);

        // Agregar productos al vendedor1 (Ana)
        vendedor2.agregarProducto(new Producto(
                "Teléfono", "Electrónica", 800.0, "Vendido",
                new Image(Objects.requireNonNull(DataUtil.class.getResource("/images/telefono.jpg")).toExternalForm()),
                LocalDateTime.of(2024,11,25,11,32)
        ));
        vendedor2.agregarProducto(new Producto(
                "Tablet", "Electrónica", 400.0, "Publicado",
                new Image(Objects.requireNonNull(DataUtil.class.getResource("/images/tablet.jpg")).toExternalForm()),
                LocalDateTime.of(2024,11,19,11,32)
        ));

        // Agregar productos al vendedor2 (Luis)
        vendedor3.agregarProducto(new Producto(
                "Monitor", "Electrónica", 200.0, "Publicado",
                new Image(Objects.requireNonNull(DataUtil.class.getResource("/images/monitor.jpg")).toExternalForm()),
                LocalDateTime.of(2024,11,7,11,32)
        ));
        vendedor3.agregarProducto(new Producto(
                "Teclado", "Accesorios", 30.0, "Cancelado",
                new Image(Objects.requireNonNull(DataUtil.class.getResource("/images/teclado.jpg")).toExternalForm()),
                LocalDateTime.of(2024,11,2,11,32)
        ));

        // Agregar productos al vendedor3 (Carlos)
        Producto laptop = new Producto(
                "Laptop", "Tecnología", 1500.0, "Publicado",
                new Image(Objects.requireNonNull(DataUtil.class.getResource("/images/laptop.jpg")).toExternalForm()),
                LocalDateTime.of(2024,11,5,11,32)
        );
        laptop.agregarLike("anag");
        laptop.agregarComentario(new Comentario("Muy buen producto, estoy interesada.", "Ana"));
        vendedor4.agregarProducto(laptop);

        Producto impresora = new Producto(
                "Impresora", "Oficina", 250.0, "Publicado",
                new Image(Objects.requireNonNull(DataUtil.class.getResource("/images/impresora.png")).toExternalForm()),
                LocalDateTime.of(2024,11,12,11,32)
        );
        impresora.agregarLike("luism");
        impresora.agregarComentario(new Comentario("¿Tiene garantía?", "Luis"));
        vendedor4.agregarProducto(impresora);

        // Agregar productos al vendedor4 (María)
        vendedor5.agregarProducto(new Producto(
                "Mesa", "Muebles", 100.0, "Publicado",
                new Image(Objects.requireNonNull(DataUtil.class.getResource("/images/mesa.jpg")).toExternalForm()),
                LocalDateTime.of(2024,11,10,11,32)
        ));
        vendedor5.agregarProducto(new Producto(
                "Silla", "Muebles", 50.0, "Publicado",
                new Image(Objects.requireNonNull(DataUtil.class.getResource("/images/silla.jpg")).toExternalForm()),
                LocalDateTime.of(2024,11,9,11,32)
        ));

        // Relaciones de contactos
        vendedor2.agregarContacto(vendedor3); // Ana y Luis son c   ontactos
        vendedor4.agregarContacto(vendedor5); // Carlos y María son contactos
        // Enviar solicitudes
        System.out.println("Enviando solicitudes iniciales...");
        vendedor2.enviarSolicitud(vendedor4); // Ana envía solicitud a Carlos
        vendedor3.enviarSolicitud(vendedor5); // Luis envía solicitud a María

        // Validar solicitudes recibidas
        System.out.println("Solicitudes recibidas por Carlos: " + vendedor4.getSolicitudesRecibidas());
        System.out.println("Solicitudes recibidas por María: " + vendedor5.getSolicitudesRecibidas());
        // Agregar vendedores a la lista
        vendedores.add(maestro);
        vendedores.add(vendedor2);
        vendedores.add(vendedor3);
        vendedores.add(vendedor4);
        vendedores.add(vendedor5);

        return vendedores;

    }
}
