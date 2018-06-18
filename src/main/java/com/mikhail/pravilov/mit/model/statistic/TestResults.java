package com.mikhail.pravilov.mit.model.statistic;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class TestResults {
    private final AverageServerMetric sortTimeMetric;
    private final AverageServerMetric clientTimeMetric;
    private final AverageClientMetric requestTimeMetric;

    public TestResults(TestConfiguration testConfiguration) {
        sortTimeMetric = new AverageServerMetric(testConfiguration);
        clientTimeMetric = new AverageServerMetric(testConfiguration);
        requestTimeMetric = new AverageClientMetric(testConfiguration);
    }

    public void addSortTime(long elapsedTime) {
        sortTimeMetric.addMetricValue(elapsedTime);
    }

    public Map<Integer, Double> getSortTimes() {
        return sortTimeMetric.getParameterToMetric();
    }

    public void addClientTime(long elapsedTime) {
        clientTimeMetric.addMetricValue(elapsedTime);
    }

    public Map<Integer, Double> getClientTimes() {
        return clientTimeMetric.getParameterToMetric();
    }

    public void addRequestTime(long elapsedTime) {
        requestTimeMetric.addMetricValue(elapsedTime);
    }

    public Map<Integer, Double> getRequestTimes() {
        return requestTimeMetric.getParameterToMetric();
    }

    public void saveToFiles() throws IOException {
        sortTimeMetric.saveToFile(Paths.get("./logs/sortTimeMetric"));
        clientTimeMetric.saveToFile(Paths.get("./logs/clientTimeMetric"));
        requestTimeMetric.saveToFile(Paths.get("./logs/requestTimeMetric"));
    }
}
