package co.edu.uniquindio.marketplace.marketplaceapp.patrones.decorator;

import co.edu.uniquindio.marketplace.marketplaceapp.services.IReporte;

/**
 * Clase abstracta para implementar el patrón Decorator.
 * Extiende las funcionalidades de un reporte de manera dinámica.
 */
public abstract class ReporteDecorator implements IReporte {

    private final IReporte reporte;

    public ReporteDecorator(IReporte reporte) {
        this.reporte = reporte;
    }

    @Override
    public String generarReporte() {
        return reporte.generarReporte();
    }
}
