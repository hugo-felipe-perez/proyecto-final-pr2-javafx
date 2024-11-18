package co.edu.uniquindio.marketplace.marketplaceapp.patrones.adapter;

import javafx.scene.chart.XYChart;

public class EstadisticasAdapter {
    public XYChart.Series<String, Number> adaptarDatosParaGrafico() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Producto A", 50));
        series.getData().add(new XYChart.Data<>("Producto B", 30));
        return series;
    }
}