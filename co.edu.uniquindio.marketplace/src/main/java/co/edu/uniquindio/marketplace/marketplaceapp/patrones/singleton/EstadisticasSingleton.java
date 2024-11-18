package co.edu.uniquindio.marketplace.marketplaceapp.patrones.singleton;

import java.util.HashMap;
import java.util.Map;

public class EstadisticasSingleton {
    private static EstadisticasSingleton instance;
    private final Map<String, Object> datos;

    private EstadisticasSingleton() {
        datos = new HashMap<>();
    }

    public static EstadisticasSingleton getInstance() {
        if (instance == null) {
            instance = new EstadisticasSingleton();
        }
        return instance;
    }

    public void registrarDato(String clave, Object valor) {
        datos.put(clave, valor);
    }

    public Object obtenerDato(String clave) {
        return datos.get(clave);
    }
}