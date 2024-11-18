package co.edu.uniquindio.marketplace.marketplaceapp.patrones.factorymethod;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

public class GraficoBarFactory extends GraficoFactory {
    @Override
    public BarChart<String, Number> crearGrafico() {
        return new BarChart<>(new CategoryAxis(), new NumberAxis());
    }
}