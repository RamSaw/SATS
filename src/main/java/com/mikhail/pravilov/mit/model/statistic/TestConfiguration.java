package com.mikhail.pravilov.mit.model.statistic;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mikhail.pravilov.mit.model.protocol.TestConfigurationProtos;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import static com.mikhail.pravilov.mit.model.statistic.TestConfiguration.ChangingParameter.*;

public class TestConfiguration {
    @NotNull
    private static final BiMap<ChangingParameter, TestConfigurationProtos.TestConfiguration.ChangingParameter>
            EQUAL_CHANGING_PARAMETERS = HashBiMap.create();

    static {
        EQUAL_CHANGING_PARAMETERS.put(NUMBER_OF_ELEMENTS,
                TestConfigurationProtos.TestConfiguration.ChangingParameter.NUMBER_OF_ELEMENTS);
        EQUAL_CHANGING_PARAMETERS.put(NUMBER_OF_CLIENTS,
                TestConfigurationProtos.TestConfiguration.ChangingParameter.NUMBER_OF_CLIENTS);
        EQUAL_CHANGING_PARAMETERS.put(TIME_BETWEEN_REQUESTS,
                TestConfigurationProtos.TestConfiguration.ChangingParameter.TIME_BETWEEN_REQUESTS);
    }

    private final ServerType serverType;
    private ChangingParameter changingParameter;
    private Integer numberOfRequests;
    private Integer numberOfElements;
    private Integer numberOfClients;
    private Integer timeBetweenRequests;
    private Integer changingStep;
    private Pair<Integer, Integer> changingBound;

    public TestConfiguration(ServerType serverType) {
        this.serverType = serverType;
    }

    public static TestConfigurationProtos.TestConfiguration getTestConfigurationMessage
            (@NotNull TestConfiguration testConfiguration) {
        return TestConfigurationProtos.TestConfiguration
                .newBuilder()
                .setChangingParameter(EQUAL_CHANGING_PARAMETERS.get(testConfiguration.getChangingParameter()))
                .setNumberOfClients(testConfiguration.getNumberOfClients() == null ? testConfiguration.getChangingBound().getKey() : testConfiguration.getNumberOfClients())
                .setNumberOfRequests(testConfiguration.getNumberOfRequests() == null ? testConfiguration.getChangingBound().getKey() : testConfiguration.getNumberOfRequests())
                .setNumberOfElements(testConfiguration.getNumberOfElements() == null ? testConfiguration.getChangingBound().getKey() : testConfiguration.getNumberOfElements())
                .setChangingStep(testConfiguration.getChangingStep())
                .setFromBound(testConfiguration.getChangingBound().getKey())
                .setToBound(testConfiguration.getChangingBound().getValue())
                .setTimeBetweenRequests(testConfiguration.getTimeBetweenRequests())
                .build();
    }

    public static TestConfiguration getTestConfiguration(@NotNull TestConfigurationProtos.TestConfiguration testConfigurationMessage) {
        TestConfiguration testConfiguration = new TestConfiguration(null);
        testConfiguration.setChangingParameter(EQUAL_CHANGING_PARAMETERS.inverse().get(testConfigurationMessage.getChangingParameter()));
        testConfiguration.setNumberOfClients((int) testConfigurationMessage.getNumberOfClients());
        testConfiguration.setNumberOfRequests((int) testConfigurationMessage.getNumberOfRequests());
        testConfiguration.setNumberOfElements((int) testConfigurationMessage.getNumberOfElements());
        testConfiguration.setChangingStep((int) testConfigurationMessage.getChangingStep());
        testConfiguration.setChangingBound(new Pair<>((int) testConfigurationMessage.getFromBound(),
                (int) testConfigurationMessage.getToBound()));
        testConfiguration.setTimeBetweenRequests((int) testConfigurationMessage.getTimeBetweenRequests());
        return testConfiguration;
    }

    public ChangingParameter getChangingParameter() {
        return changingParameter;
    }

    public void setChangingParameter(ChangingParameter changingParameter) {
        this.changingParameter = changingParameter;
    }

    public Integer getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(Integer numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Integer getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(Integer numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public Integer getTimeBetweenRequests() {
        return timeBetweenRequests;
    }

    public void setTimeBetweenRequests(Integer timeBetweenRequests) {
        this.timeBetweenRequests = timeBetweenRequests;
    }

    public Integer getChangingStep() {
        return changingStep;
    }

    public void setChangingStep(Integer changingStep) {
        this.changingStep = changingStep;
    }

    public Pair<Integer, Integer> getChangingBound() {
        return changingBound;
    }

    public void setChangingBound(Pair<Integer, Integer> changingBound) {
        this.changingBound = changingBound;
    }

    public Integer getNumberOfIterations() {
        return (getChangingBound().getValue() - getChangingBound().getKey()) / getChangingStep() + 1;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public int getTotalNumberOfTests() {
        int totalNumberOfTests;
        if (getChangingParameter() != NUMBER_OF_CLIENTS) {
            totalNumberOfTests = getNumberOfClients() * getNumberOfIterations();
        } else {
            totalNumberOfTests = 2 * getNumberOfClients()
                    + (getNumberOfIterations() - 1) * getChangingStep();
            totalNumberOfTests *= getNumberOfIterations();
            totalNumberOfTests /= 2;
        }
        return totalNumberOfTests;
    }

    public enum ChangingParameter {
        NUMBER_OF_ELEMENTS, NUMBER_OF_CLIENTS, TIME_BETWEEN_REQUESTS
    }

    public enum ServerType {
        ONE_THREAD, MULTITHREADING, UNBLOCKING
    }
}
