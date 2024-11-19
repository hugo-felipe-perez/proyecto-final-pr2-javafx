package co.edu.uniquindio.marketplace.marketplaceapp.patrones.decorator;

import co.edu.uniquindio.marketplace.marketplaceapp.services.IReporte;

public class ReporteConEstilo extends ReporteDecorator {

    public ReporteConEstilo(IReporte reporte) {
        super(reporte);
    }

    @Override
    public String generarReporte() {
        StringBuilder reporteConEstilo = new StringBuilder();

        reporteConEstilo.append("===================================\n");
        reporteConEstilo.append("       🌟 Reporte con Estilo 🌟     \n");
        reporteConEstilo.append("===================================\n");

        reporteConEstilo.append(super.generarReporte());

        reporteConEstilo.append("\n===================================\n");
        reporteConEstilo.append("        📊 Fin del Reporte 📊       \n");
        reporteConEstilo.append("===================================\n");

        return reporteConEstilo.toString();
    }
}