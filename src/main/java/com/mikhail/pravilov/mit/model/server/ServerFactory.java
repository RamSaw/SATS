package com.mikhail.pravilov.mit.model.server;

import com.mikhail.pravilov.mit.model.statistic.TestConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ServerFactory {
    @NotNull
    public static Server getServerByConfiguration(@NotNull TestConfiguration serverConfiguration) throws IOException {
        switch (serverConfiguration.getServerType()) {
            case ONE_THREAD:
                return new OneThreadServer(serverConfiguration);
            case MULTITHREADING:
                return new MultiThreadingServer(serverConfiguration);
            case UNBLOCKING:
                return new UnblockingServer(serverConfiguration);
            default:
                throw new IllegalStateException("No such server configuration is supported!");
        }
    }
}
