package com.mikhail.pravilov.mit.view;

import com.mikhail.pravilov.mit.model.server.Server;
import com.mikhail.pravilov.mit.model.server.ServerFactory;
import com.mikhail.pravilov.mit.model.statistic.TestConfiguration;
import com.mikhail.pravilov.mit.model.statistic.TestResults;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public class ChartsSceneSupplier implements SceneSupplier {
    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number, Number> requestTimeChart = new LineChart<>(xAxis, yAxis);
    private TestConfiguration testConfiguration;

    public ChartsSceneSupplier(TestConfiguration testConfiguration) throws IOException {
        this.testConfiguration = testConfiguration;
        Server server = ServerFactory.getServerByConfiguration(testConfiguration);
        TestResults testResults = server.test();
        testResults.saveToFiles();
        drawCharts(testResults);
    }

    private void drawCharts(@NotNull TestResults testResults) {
        xAxis.setLabel("Changing Parameter: " + testConfiguration.getChangingParameter().toString());
        requestTimeChart.setTitle("Test Results");

        XYChart.Series<Number, Number> sortTimeSeries = new XYChart.Series<>();
        sortTimeSeries.setName("Sort Time Series");
        for (Map.Entry<Integer, Double> result : testResults.getSortTimes().entrySet()) {
            sortTimeSeries.getData().add(new XYChart.Data<>(result.getKey(), result.getValue()));
        }

        XYChart.Series<Number, Number> clientTimeSeries = new XYChart.Series<>();
        clientTimeSeries.setName("Client Time Series");
        for (Map.Entry<Integer, Double> result : testResults.getClientTimes().entrySet()) {
            clientTimeSeries.getData().add(new XYChart.Data<>(result.getKey(), result.getValue()));
        }

        XYChart.Series<Number, Number> requestTimeSeries = new XYChart.Series<>();
        requestTimeSeries.setName("Request Time Series");
        for (Map.Entry<Integer, Double> result : testResults.getRequestTimes().entrySet()) {
            requestTimeSeries.getData().add(new XYChart.Data<>(result.getKey(), result.getValue()));
        }

        requestTimeChart.getData().add(sortTimeSeries);
        requestTimeChart.getData().add(clientTimeSeries);
        requestTimeChart.getData().add(requestTimeSeries);
    }

    @Override
    public Scene getScene() {
        return new Scene(requestTimeChart, 800, 900);
    }
}
