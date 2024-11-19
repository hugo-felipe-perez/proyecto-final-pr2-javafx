package co.edu.uniquindio.marketplace.marketplaceapp.patrones.singleton;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Producto;
import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.utils.DataUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton que centraliza la lógica de las estadísticas del marketplace.
 */
public class EstadisticasSingleton {

    // Instancia única de la clase
    private static EstadisticasSingleton instance;

    // Constructor privado para evitar instanciación directa
    private EstadisticasSingleton() {}

    // Método para obtener la instancia única
    public static EstadisticasSingleton getInstance() {
        if (instance == null) {
            instance = new EstadisticasSingleton();
        }
        return instance;
    }

    /**
     * Calcula la cantidad de mensajes enviados entre dos vendedores.
     *
     * @param vendedor1 El primer vendedor.
     * @param vendedor2 El segundo vendedor.
     * @return La cantidad de mensajes enviados entre los dos vendedores.
     */
    public int obtenerCantidadMensajesEntreVendedores(Vendedor vendedor1, Vendedor vendedor2) {
        return (int) vendedor1.getMensajesEnviados().stream()
                .filter(mensaje -> mensaje.getDestinatario().equals(vendedor2.getUsername()))
                .count();
    }

    /**
     * Obtiene los productos publicados entre dos fechas específicas.
     *
     * @param fechaInicio La fecha de inicio.
     * @param fechaFin La fecha de fin.
     * @return Una lista de productos publicados entre las dos fechas.
     */
    public List<Producto> obtenerProductosPublicadosEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return DataUtil.cargarVendedoresIniciales().stream()
                .flatMap(vendedor -> vendedor.getProductos().stream())
                .filter(producto -> {
                    LocalDate fechaPublicacion = producto.getFechaPublicacion().toLocalDate();
                    return (fechaPublicacion.isEqual(fechaInicio) || fechaPublicacion.isAfter(fechaInicio)) &&
                            (fechaPublicacion.isEqual(fechaFin) || fechaPublicacion.isBefore(fechaFin));
                })
                .collect(Collectors.toList());
    }

    /**
     * Calcula la cantidad de productos publicados por un vendedor.
     *
     * @param vendedor El vendedor.
     * @return La cantidad de productos publicados por el vendedor.
     */
    public int obtenerProductosPublicadosPorVendedor(Vendedor vendedor) {
        return vendedor.getProductos().size();
    }

    /**
     * Calcula la cantidad de contactos que tiene un vendedor.
     *
     * @param vendedor El vendedor.
     * @return La cantidad de contactos del vendedor.
     */
    public int obtenerCantidadDeContactosPorVendedor(Vendedor vendedor) {
        return vendedor.getContactos().size();
    }

    /**
     * Obtiene el Top 10 de productos con más "Me gusta".
     *
     * @return Una lista con los 10 productos más populares.
     */
    public List<Producto> obtenerTopProductosConMasLikes() {
        return DataUtil.cargarVendedoresIniciales().stream()
                .flatMap(vendedor -> vendedor.getProductos().stream())
                .sorted((p1, p2) -> Integer.compare(p2.getLikes().size(), p1.getLikes().size()))
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el total de productos publicados en el marketplace.
     *
     * @return El número total de productos publicados.
     */
    public int obtenerTotalProductosPublicados() {
        return DataUtil.cargarVendedoresIniciales().stream()
                .mapToInt(vendedor -> vendedor.getProductos().size())
                .sum();
    }

    /**
     * Obtiene el total de mensajes enviados en el marketplace.
     *
     * @return El número total de mensajes enviados.
     */
    public int obtenerTotalMensajesEnviados() {
        return DataUtil.cargarVendedoresIniciales().stream()
                .mapToInt(vendedor -> vendedor.getMensajesEnviados().size())
                .sum();
    }

    /**
     * Obtiene el total de contactos en el marketplace.
     *
     * @return El número total de contactos en el marketplace.
     */
    public int obtenerTotalContactos() {
        return DataUtil.cargarVendedoresIniciales().stream()
                .mapToInt(vendedor -> vendedor.getContactos().size())
                .sum();
    }
}
