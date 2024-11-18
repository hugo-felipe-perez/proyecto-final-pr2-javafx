package co.edu.uniquindio.marketplace.marketplaceapp.patrones.strategy;

import java.util.List;

import co.edu.uniquindio.marketplace.marketplaceapp.services.EstrategiaEstadistica;

public class ProductosPublicadosStrategy implements EstrategiaEstadistica {
    private final List<String> productos;

    public ProductosPublicadosStrategy(List<String> productos) {
        this.productos = productos;
    }

    @Override
    public String calcularEstadistica() {
        return "Productos publicados: " + productos.size();
    }
}