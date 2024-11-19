package co.edu.uniquindio.marketplace.marketplaceapp.patrones.decorator;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.services.IReporte;

import java.util.List;

public class ReporteConEstadisticas extends ReporteDecorator {

    private List<Vendedor> vendedores;

    public ReporteConEstadisticas(IReporte reporte, List<Vendedor> vendedores) {
        super(reporte);
        this.vendedores = vendedores;
    }

    @Override
    public String generarReporte() {
        StringBuilder estadisticas = new StringBuilder(super.generarReporte());
        estadisticas.append("\nEstadísticas Detalladas:\n");

        for (Vendedor vendedor : vendedores) {
            estadisticas.append("Usuario: ").append(vendedor.getNombre()).append(" (").append(vendedor.getUsername()).append(")\n")
                    .append("- Productos publicados: ").append(vendedor.getProductos().size()).append("\n")
                    .append("- Mensajes enviados: ").append(vendedor.getMensajesEnviados().size()).append("\n")
                    .append("- Contactos: ").append(vendedor.getContactos().size()).append("\n\n");
        }
        return estadisticas.toString();
    }
}