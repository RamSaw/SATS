package com.mikhail.pravilov.mit.model.client;

import com.mikhail.pravilov.mit.model.protocol.TestConfigurationProtos;
import com.mikhail.pravilov.mit.model.statistic.TestConfiguration;
import org.apache.commons.io.input.BoundedInputStream;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientRunner {
    public static void main(@NotNull String[] args) throws IOException, InterruptedException {
        String hostname = args[0];
        int port = Integer.valueOf(args[1]);
        Socket clientRunnerSocket = new Socket(hostname, port);
        DataOutputStream dataOutputStream = new DataOutputStream(clientRunnerSocket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(clientRunnerSocket.getInputStream());
        dataOutputStream.writeLong(-1);
        TestConfigurationProtos.TestConfiguration testConfigurationMessage =
                TestConfigurationProtos.TestConfiguration.parseFrom(new BoundedInputStream(dataInputStream, dataInputStream.readLong()));
        clientRunnerSocket.close();
        TestConfiguration testConfiguration = TestConfiguration.getTestConfiguration(testConfigurationMessage);

        int numberOfElements = testConfiguration.getNumberOfElements();
        int numberOfRequests = testConfiguration.getNumberOfRequests();
        int timeBetweenRequests = testConfiguration.getTimeBetweenRequests();
        int numberOfClients = testConfiguration.getNumberOfClients();
        int step = testConfiguration.getChangingStep();
        int numberOfIterations = testConfiguration.getNumberOfIterations();
        for (int k = 0; k < numberOfIterations; k++) {
            Thread[] clientThreads = new Thread[numberOfClients];
            for (int i = 0; i < clientThreads.length; i++) {
                clientThreads[i] = new Thread(new Client(hostname, port, numberOfElements, numberOfRequests, timeBetweenRequests));
            }
            for (Thread clientThread : clientThreads) {
                clientThread.start();
            }
            for (Thread clientThread : clientThreads) {
                clientThread.join();
            }

            switch (testConfiguration.getChangingParameter()) {
                case NUMBER_OF_CLIENTS:
                    numberOfClients += step;
                    break;
                case NUMBER_OF_ELEMENTS:
                    numberOfElements += step;
                    break;
                case TIME_BETWEEN_REQUESTS:
                    timeBetweenRequests += step;
                    break;
            }
        }
    }
}
