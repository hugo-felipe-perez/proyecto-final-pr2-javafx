package co.edu.uniquindio.marketplace.marketplaceapp.model;

import java.util.List;

public class Estadistica {
    public static int contarMensajesEntre(Vendedor vendedor1, Vendedor vendedor2) {
        // Supongamos que hay un sistema de mensajería implementado
        return 0; // Implementar con datos reales
    }

    public static int contarProductosPorVendedor(Vendedor vendedor) {
        return vendedor.getProductos().size();
    }

    public static int contarContactosPorVendedor(Vendedor vendedor) {
        return vendedor.getContactos().size();
    }

    public static Producto topProductoConMasLikes(List<Producto> productos) {
        return productos.stream()
                .max((p1, p2) -> Integer.compare(p1.getLikes().size(), p2.getLikes().size()))
                .orElse(null);
    }
}
