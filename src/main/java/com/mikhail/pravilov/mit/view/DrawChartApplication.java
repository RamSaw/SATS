package com.mikhail.pravilov.mit.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class DrawChartApplication extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String changingParameter = "ELEMENTS";
        String metricNumber = " (number 1)";
        String metricName = "SORT_TIME";
        Path oneThread = Paths.get("./logsForGraphs/OneThread/" + metricName + "_" + changingParameter);
        Path multiThreading = Paths.get("./logsForGraphs/MultiThreading/" + metricName + "_" + changingParameter);
        Path unblocking = Paths.get("./logsForGraphs/Unblocking/" + metricName + "_" + changingParameter);

        primaryStage.setTitle("Data");
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(changingParameter);
        yAxis.setLabel("TIME");
        LineChart<Number, Number> dataChart = new LineChart<>(xAxis, yAxis);
        dataChart.setTitle("Metric: " + metricName + metricNumber);

        XYChart.Series<Number, Number> oneThreadSeries = new XYChart.Series<>();
        oneThreadSeries.setName("One Thread");
        Scanner oneThreadScanner = new Scanner(oneThread.toFile());
        while (!oneThreadScanner.nextLine().equals("PARAMETER -> TIME")) {
        }
        while (oneThreadScanner.hasNextLine()) {
            String[] params = oneThreadScanner.nextLine().split(" ");
            oneThreadSeries.getData().add(new XYChart.Data<>(Integer.parseInt(params[0]), Double.parseDouble(params[1])));
        }

        XYChart.Series<Number, Number> multiThreadingSeries = new XYChart.Series<>();
        multiThreadingSeries.setName("Multi Threading");
        Scanner multiThreadingScanner = new Scanner(multiThreading.toFile());
        while (!multiThreadingScanner.nextLine().equals("PARAMETER -> TIME")) {
        }
        while (multiThreadingScanner.hasNextLine()) {
            String[] params = multiThreadingScanner.nextLine().split(" ");
            multiThreadingSeries.getData().add(new XYChart.Data<>(Integer.parseInt(params[0]), Double.parseDouble(params[1])));
        }

        XYChart.Series<Number, Number> unblockingSeries = new XYChart.Series<>();
        unblockingSeries.setName("Unblocking");
        Scanner unblockingScanner = new Scanner(unblocking.toFile());
        while (!unblockingScanner.nextLine().equals("PARAMETER -> TIME")) {
        }
        while (unblockingScanner.hasNextLine()) {
            String[] params = unblockingScanner.nextLine().split(" ");
            unblockingSeries.getData().add(new XYChart.Data<>(Integer.parseInt(params[0]), Double.parseDouble(params[1])));
        }

        dataChart.getData().add(oneThreadSeries);
        dataChart.getData().add(multiThreadingSeries);
        dataChart.getData().add(unblockingSeries);

        Scene scene = new Scene(dataChart, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);

        primaryStage.show();
    }
}

