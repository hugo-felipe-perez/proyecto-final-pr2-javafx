package co.edu.uniquindio.marketplace.marketplaceapp.patrones.strategy;

import co.edu.uniquindio.marketplace.marketplaceapp.services.EstrategiaEstadistica;

public class CantidadMensajesStrategy implements EstrategiaEstadistica {
    @Override
    public String calcularEstadistica() {
        return "Cantidad de mensajes enviados: 120";
    }
}