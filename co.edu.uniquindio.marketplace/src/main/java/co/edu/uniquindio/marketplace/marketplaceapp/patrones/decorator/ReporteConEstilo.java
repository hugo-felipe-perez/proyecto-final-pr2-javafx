package co.edu.uniquindio.marketplace.marketplaceapp.patrones.decorator;

public class ReporteConEstilo extends ReporteDecorator {
    public ReporteConEstilo(ReporteDecorator componente) {
        super(componente);
    }

    @Override
    public String getContenido() {
        return componente.getContenido() + "\n-- Agregado con Estilo Fancy --";
    }
}