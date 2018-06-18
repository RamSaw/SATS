package com.mikhail.pravilov.mit.model.server;

import com.mikhail.pravilov.mit.model.statistic.TestConfiguration;
import com.mikhail.pravilov.mit.model.statistic.TestResults;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

abstract public class Server {
    final static int PORT = 4444;
    final TestConfiguration testConfiguration;
    final TestResults testResults;

    public Server(@NotNull TestConfiguration testConfiguration) {
        this.testConfiguration = testConfiguration;
        testResults = new TestResults(testConfiguration);
    }

    public TestResults test() throws IOException, InterruptedException {
        ArrayList<Thread> clientThreads = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            int totalNumberOfAcceptedClients = testConfiguration.getTotalNumberOfTests() + 1;
            int currentNumberOfTests = 0;
            while (!serverSocket.isClosed() && currentNumberOfTests < totalNumberOfAcceptedClients) {
                Socket clientSocket = serverSocket.accept();
                currentNumberOfTests++;
                ClientHandler clientHandler = getNewClientHandler(clientSocket);
                Thread threadForClientHandler = new Thread(clientHandler);
                threadForClientHandler.start();
                clientThreads.add(threadForClientHandler);
            }
        }
        for (Thread clientThread : clientThreads) {
            try {
                clientThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return testResults;
    }

    abstract ClientHandler getNewClientHandler(Socket clientSocket) throws IOException;
}
