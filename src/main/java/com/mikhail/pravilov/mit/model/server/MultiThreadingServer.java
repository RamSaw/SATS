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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MultiThreadingServer extends Server {
    private ExecutorService sortingThreads = Executors.newFixedThreadPool(3);

    MultiThreadingServer(@NotNull TestConfiguration testConfiguration) {
        super(testConfiguration);
    }

    @Override
    ClientHandler getNewClientHandler(Socket clientSocket) throws IOException {
        return new MultiThreadingClientHandler(clientSocket, testConfiguration, testResults);
    }

    private class MultiThreadingClientHandler extends ClientHandler {
        @NotNull
        final ExecutorService responseWriter = Executors.newSingleThreadExecutor();

        MultiThreadingClientHandler(@NotNull Socket clientSocket, @NotNull TestConfiguration testConfiguration, @NotNull TestResults testResults) throws IOException {
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
                    /*
                    long stopTime = System.currentTimeMillis();
                    long elapsedTime = stopTime - startTime;
                    synchronized (testResults) {
                        testResults.addClientTime(elapsedTime);
                    }
                    */
                    executedRequests++;
                }
                if (!isTestConfigurationRequest) {
                    synchronized (testResults) {
                        testResults.addRequestTime(dataInputStream.readLong());
                    }
                }
                // closeClientConnection();
            } catch (IOException e) {
                System.err.println("Cannot write or read to client, error: " + e.getLocalizedMessage());
            }
        }

        @Override
        void processRequest(long messageSize) throws IOException {
            SortArrayProtos.SortArray arrayToSortMessage = SortArrayProtos.SortArray.parseFrom(new BoundedInputStream(dataInputStream, messageSize));
            sortingThreads.submit(() -> {
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
                try {
                    dataOutputStream.writeLong(sortedArrayMessage.getSerializedSize());
                    sortedArrayMessage.writeTo(dataOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                responseWriter.submit(() -> {
                    SortArrayProtos.SortArray sortedArrayMessage = arrayToSortMessage.toBuilder().clearNumber().addAllNumber(sortedList).build();
                    try {
                        dataOutputStream.writeLong(sortedArrayMessage.getSerializedSize());
                        sortedArrayMessage.writeTo(dataOutputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                */
            });
        }

        @Override
        void sendTestConfiguration() {
            sortingThreads.submit(() -> {
                TestConfigurationProtos.TestConfiguration testConfigurationMessage = TestConfiguration.getTestConfigurationMessage(testConfiguration);
                try {
                    dataOutputStream.writeLong(testConfigurationMessage.getSerializedSize());
                    testConfigurationMessage.writeTo(dataOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                TestConfigurationProtos.TestConfiguration testConfigurationMessage = TestConfiguration.getTestConfigurationMessage(testConfiguration);
                responseWriter.submit(() -> {
                    try {
                        dataOutputStream.writeLong(testConfigurationMessage.getSerializedSize());
                        testConfigurationMessage.writeTo(dataOutputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                */
            });
        }


        void closeClientConnection() {
            // seems like no need to wait because all must be sent
            /*
            responseWriter.shutdown();
            try {
                responseWriter.awaitTermination(60, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new IllegalStateException("Response is writing too long", e);
            }
            super.closeClientConnection();
            */
        }
    }
}
