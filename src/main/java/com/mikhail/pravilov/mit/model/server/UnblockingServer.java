package com.mikhail.pravilov.mit.model.server;

import com.mikhail.pravilov.mit.model.statistic.TestConfiguration;
import com.mikhail.pravilov.mit.model.statistic.TestResults;
import org.jetbrains.annotations.NotNull;

import java.net.Socket;

class UnblockingServer extends Server {
    UnblockingServer(@NotNull TestConfiguration testConfiguration) {
        super(testConfiguration);
    }

    @Override
    public TestResults test() {
        return null;
    }

    @Override
    ClientHandler getNewClientHandler(Socket clientSocket) {
        return null;
    }
}
