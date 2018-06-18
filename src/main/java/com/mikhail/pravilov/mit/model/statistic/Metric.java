package com.mikhail.pravilov.mit.model.statistic;

import org.jetbrains.annotations.NotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

abstract class Metric {
    final ConcurrentMap<Integer, Double> parameterToMetric = new ConcurrentHashMap<>();
    final TestConfiguration testConfiguration;

    Metric(@NotNull TestConfiguration testConfiguration) {
        this.testConfiguration = testConfiguration;
    }

    void saveToFile(Path path) throws IOException {
        try (FileWriter fileWriter = new FileWriter(path.toFile())) {
            fileWriter.write("===PARAMETERS===\n");
            fileWriter.write("Server type: " + testConfiguration.getServerType().toString() + "\n");
            fileWriter.write("Number of clients: " + testConfiguration.getNumberOfClients().toString() + "\n");
            fileWriter.write("Number of requests: " + testConfiguration.getNumberOfRequests().toString() + "\n");
            fileWriter.write("Number of elements: " + testConfiguration.getNumberOfElements().toString() + "\n");
            fileWriter.write("Number of time between requests: " + testConfiguration.getTimeBetweenRequests().toString() + "\n");
            fileWriter.write("Changing parameter: " + testConfiguration.getChangingParameter().toString() + "\n");
            fileWriter.write("From: " + testConfiguration.getChangingBound().getKey() + "\n");
            fileWriter.write("To: " + testConfiguration.getChangingBound().getValue() + "\n");
            fileWriter.write('\n');
            fileWriter.write("===VALUES===\n");
            fileWriter.write("PARAMETER -> TIME\n");
            for (Map.Entry<Integer, Double> result : parameterToMetric.entrySet()) {
                fileWriter.write(result.getKey().toString());
                fileWriter.write(' ');
                fileWriter.write(result.getValue().toString());
                fileWriter.write('\n');
            }
            fileWriter.flush();
        }
    }

    abstract void addMetricValue(long metricValue);

    Map<Integer, Double> getParameterToMetric() {
        return parameterToMetric;
    }
}
