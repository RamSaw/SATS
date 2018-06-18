package com.mikhail.pravilov.mit.model.statistic;

import org.jetbrains.annotations.NotNull;

import static com.mikhail.pravilov.mit.model.statistic.TestConfiguration.ChangingParameter.NUMBER_OF_CLIENTS;

class AverageClientMetric extends Metric {
    private int accepted;
    private int totalSum;
    private int neededToBeAccept;
    private int currentValueOfParameter;

    AverageClientMetric(@NotNull TestConfiguration testConfiguration) {
        super(testConfiguration);
        switch (testConfiguration.getChangingParameter()) {
            case NUMBER_OF_CLIENTS:
                currentValueOfParameter = testConfiguration.getNumberOfClients();
                break;
            case NUMBER_OF_ELEMENTS:
                currentValueOfParameter = testConfiguration.getNumberOfElements();
                break;
            case TIME_BETWEEN_REQUESTS:
                currentValueOfParameter = testConfiguration.getTimeBetweenRequests();
                break;
        }
        accepted = 0;
        totalSum = 0;
        neededToBeAccept = testConfiguration.getNumberOfClients();
    }

    private void updateToNextTest() {
        parameterToMetric.put(currentValueOfParameter, (double) totalSum / (accepted * testConfiguration.getNumberOfRequests()));
        accepted = 0;
        totalSum = 0;
        currentValueOfParameter += testConfiguration.getChangingStep();
        if (testConfiguration.getChangingParameter() == NUMBER_OF_CLIENTS) {
            neededToBeAccept = currentValueOfParameter;
        }
    }

    @Override
    void addMetricValue(long metricValue) {
        totalSum += metricValue;
        accepted++;
        if (accepted == neededToBeAccept) {
            updateToNextTest();
        }
    }
}