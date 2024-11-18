package co.edu.uniquindio.marketplace.marketplaceapp.patrones.decorator;

public class ReporteSimple extends ReporteDecorator {
    public ReporteSimple() {
        super(null);
    }

    @Override
    public String getContenido() {
        return "Reporte básico con datos estándar.";
    }
}