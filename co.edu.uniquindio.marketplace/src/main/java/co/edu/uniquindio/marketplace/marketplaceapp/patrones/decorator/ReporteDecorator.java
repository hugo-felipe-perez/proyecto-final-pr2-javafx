package co.edu.uniquindio.marketplace.marketplaceapp.patrones.decorator;

public abstract class ReporteDecorator {
    protected ReporteDecorator componente;

    public ReporteDecorator(ReporteDecorator componente) {
        this.componente = componente;
    }

    public abstract String getContenido();
}
