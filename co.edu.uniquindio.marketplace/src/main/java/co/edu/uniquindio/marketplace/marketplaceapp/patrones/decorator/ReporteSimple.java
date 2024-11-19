package co.edu.uniquindio.marketplace.marketplaceapp.patrones.decorator;

import co.edu.uniquindio.marketplace.marketplaceapp.model.Vendedor;
import co.edu.uniquindio.marketplace.marketplaceapp.services.IReporte;

import java.util.List;

public class ReporteSimple implements IReporte {

    private List<Vendedor> vendedores;

    public ReporteSimple(List<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    @Override
    public String generarReporte() {
        return "Reporte Básico de Marketplace\n" +
                "- Total de vendedores: " + vendedores.size() + "\n" +
                "- Total de productos publicados: " + vendedores.stream()
                .flatMap(v -> v.getProductos().stream()).count() + "\n";
    }
}