package co.edu.uniquindio.marketplace.marketplaceapp.patrones.observer;

import java.util.ArrayList;
import java.util.List;

import co.edu.uniquindio.marketplace.marketplaceapp.services.Observador;

public class ObservableEstadisticas {
    private final List<Observador> observadores = new ArrayList<>();

    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    public void notificar() {
        for (Observador observador : observadores) {
            observador.actualizar();
        }
    }
}