package com.mikhail.pravilov.mit.model.server;


import com.mikhail.pravilov.mit.model.statistic.TestConfiguration;
import com.mikhail.pravilov.mit.model.statistic.TestResults;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Client handler: runs in separate thread and listens to client request and responses.
 */
abstract class ClientHandler implements Runnable {
    @NotNull
    final DataInputStream dataInputStream;
    @NotNull
    final DataOutputStream dataOutputStream;
    final TestConfiguration testConfiguration;
    final TestResults testResults;

    ClientHandler(@NotNull Socket clientSocket, @NotNull TestConfiguration testConfiguration, @NotNull TestResults testResults) throws IOException {
        dataInputStream = new DataInputStream(clientSocket.getInputStream());
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        this.testConfiguration = testConfiguration;
        this.testResults = testResults;
    }

    @Override
    public abstract void run();
}
