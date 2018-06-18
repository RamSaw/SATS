package com.mikhail.pravilov.mit.model.server;

import com.mikhail.pravilov.mit.model.protocol.SortArrayProtos;
import com.mikhail.pravilov.mit.model.protocol.TestConfigurationProtos;
import com.mikhail.pravilov.mit.model.statistic.TestConfiguration;
import com.mikhail.pravilov.mit.model.statistic.TestResults;
import org.apache.commons.io.input.BoundedInputStream;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

class OneThreadServer extends Server {
    OneThreadServer(@NotNull TestConfiguration testConfiguration) {
        super(testConfiguration);
    }

    @Override
    ClientHandler getNewClientHandler(Socket clientSocket) throws IOException {
        return new OneThreadClientHandler(clientSocket, testConfiguration, testResults);
    }

    /**
     * Client handler: runs in separate thread and listens to client request and responses.
     */
    private class OneThreadClientHandler extends ClientHandler {
        OneThreadClientHandler(@NotNull Socket clientSocket, @NotNull TestConfiguration testConfiguration, @NotNull TestResults testResults) throws IOException {
            super(clientSocket, testConfiguration, testResults);
        }

        @Override
        public void run() {
            try {
                int executedRequests = 0;
                boolean isTestConfigurationRequest = false;
                while (executedRequests != testConfiguration.getNumberOfRequests()) {
                    long messageSize = dataInputStream.readLong();
                    long startTime = System.currentTimeMillis();
                    if (messageSize == -1) {
                        sendTestConfiguration();
                        isTestConfigurationRequest = true;
                        break;
                    } else {
                        processRequest(messageSize);
                    }
                    long stopTime = System.currentTimeMillis();
                    long elapsedTime = stopTime - startTime;
                    synchronized (testResults) {
                        testResults.addClientTime(elapsedTime);
                    }
                    executedRequests++;
                }
                if (!isTestConfigurationRequest) {
                    synchronized (testResults) {
                        testResults.addRequestTime(dataInputStream.readLong());
                    }
                }
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Cannot write or read to client, error: " + e.getLocalizedMessage());
            }
        }

        @Override
        void processRequest(long messageSize) throws IOException {
            SortArrayProtos.SortArray arrayToSortMessage = SortArrayProtos.SortArray.parseFrom(new BoundedInputStream(dataInputStream, messageSize));
            long[] arrayToSort = new long[arrayToSortMessage.getNumberCount()];
            int i = 0;
            for (long number : arrayToSortMessage.getNumberList()) {
                arrayToSort[i++] = number;
            }

            long startTime = System.currentTimeMillis();
            ServerUtils.bubbleSort(arrayToSort);
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            synchronized (testResults) {
                testResults.addSortTime(elapsedTime);
            }

            List<Long> sortedList = new LinkedList<>();
            for (long number : arrayToSort) {
                sortedList.add(number);
            }
            SortArrayProtos.SortArray sortedArrayMessage = arrayToSortMessage.toBuilder().clearNumber().addAllNumber(sortedList).build();
            dataOutputStream.writeLong(sortedArrayMessage.getSerializedSize());
            sortedArrayMessage.writeTo(dataOutputStream);
        }

        @Override
        void sendTestConfiguration() throws IOException {
            TestConfigurationProtos.TestConfiguration testConfigurationMessage = TestConfiguration.getTestConfigurationMessage(testConfiguration);
            dataOutputStream.writeLong(testConfigurationMessage.getSerializedSize());
            testConfigurationMessage.writeTo(dataOutputStream);
        }
    }
}
