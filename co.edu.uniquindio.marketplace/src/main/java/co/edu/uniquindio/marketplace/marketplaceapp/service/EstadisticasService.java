package co.edu.uniquindio.marketplace.marketplaceapp.service;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;

import java.util.List;
//Strategy
public class EstadisticasService {

    public int contarMensajesEntre(Vendedor vendedor1, Vendedor vendedor2) {
        // Implementar lógica cuando el sistema de mensajes esté completo.
        return 0;
    }

    public int contarProductosPorVendedor(Vendedor vendedor) {
        return vendedor.getProductos().size();
    }

    public int contarContactosPorVendedor(Vendedor vendedor) {
        return vendedor.getContactos().size();
    }

    public Producto topProductoConMasLikes(List<Producto> productos) {
        return productos.stream()
                .max((p1, p2) -> Integer.compare(p1.getLikes().size(), p2.getLikes().size()))
                .orElse(null);
    }
}