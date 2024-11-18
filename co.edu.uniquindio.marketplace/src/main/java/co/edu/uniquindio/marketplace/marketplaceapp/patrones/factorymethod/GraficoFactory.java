package co.edu.uniquindio.marketplace.marketplaceapp.patrones.factorymethod;

import javafx.scene.chart.BarChart;

public abstract class GraficoFactory {
    public abstract BarChart<String, Number> crearGrafico();
}