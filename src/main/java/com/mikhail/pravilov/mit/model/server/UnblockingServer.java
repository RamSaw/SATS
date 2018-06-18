package com.mikhail.pravilov.mit.model.server;

import com.google.protobuf.InvalidProtocolBufferException;
import com.mikhail.pravilov.mit.model.protocol.SortArrayProtos;
import com.mikhail.pravilov.mit.model.protocol.TestConfigurationProtos;
import com.mikhail.pravilov.mit.model.statistic.TestConfiguration;
import com.mikhail.pravilov.mit.model.statistic.TestResults;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class UnblockingServer extends Server {
    private final Selector readerSelector;
    private final Selector writerSelector;
    private final ExecutorService sortingThreads = Executors.newFixedThreadPool(3);
    private final CountDownLatch waiterToCompleteAllTests;
    private final ConcurrentLinkedQueue<UnblockingClientHandler> registerInReader = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<UnblockingClientHandler> registerInWriter = new ConcurrentLinkedQueue<>();

    UnblockingServer(@NotNull TestConfiguration testConfiguration) throws IOException {
        super(testConfiguration);
        waiterToCompleteAllTests = new CountDownLatch(testConfiguration.getTotalNumberOfTests());
        readerSelector = Selector.open();
        writerSelector = Selector.open();
    }

    @Override
    public TestResults test() throws IOException, InterruptedException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", PORT));
        acceptAndSendTestConfiguration(serverSocket);
        Thread readerThread = new Thread(new Reader());
        Thread writerThread = new Thread(new Writer());
        readerThread.start();
        writerThread.start();

        int totalNumberOfTests = testConfiguration.getTotalNumberOfTests();
        int currentNumberOfTests = 0;
        while (currentNumberOfTests < totalNumberOfTests) {
            acceptClient(serverSocket);
            currentNumberOfTests++;
        }
        waiterToCompleteAllTests.await();
        readerThread.interrupt();
        writerThread.interrupt();
        return testResults;
    }

    private void acceptAndSendTestConfiguration(@NotNull ServerSocketChannel serverSocket) throws IOException {
        SocketChannel clientRunner = serverSocket.accept();
        DataInputStream dataInputStream = new DataInputStream(clientRunner.socket().getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(clientRunner.socket().getOutputStream());
        long messageSize = dataInputStream.readLong();
        if (messageSize != -1) {
            throw new IllegalStateException("First client must be client runner");
        }
        sendTestConfiguration(dataOutputStream);
    }

    private void sendTestConfiguration(@NotNull DataOutputStream dataOutputStream) throws IOException {
        TestConfigurationProtos.TestConfiguration testConfigurationMessage = TestConfiguration.getTestConfigurationMessage(testConfiguration);
        dataOutputStream.writeLong(testConfigurationMessage.getSerializedSize());
        testConfigurationMessage.writeTo(dataOutputStream);
    }

    private void acceptClient(@NotNull ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        UnblockingClientHandler clientHandler = new UnblockingClientHandler(client);
        registerInReader.offer(clientHandler);
        readerSelector.wakeup();
    }

    @Override
    ClientHandler getNewClientHandler(Socket clientSocket) {
        return null;
    }

    private class Reader implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                UnblockingClientHandler clientHandler;
                while ((clientHandler = registerInReader.poll()) != null) {
                    try {
                        clientHandler.client.configureBlocking(false);
                        clientHandler.client.register(readerSelector, SelectionKey.OP_READ, clientHandler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    readerSelector.select();
                    Set<SelectionKey> selectedKeys = readerSelector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isReadable()) {
                            UnblockingClientHandler unblockingClientHandler = (UnblockingClientHandler) key.attachment();
                            unblockingClientHandler.read();
                        }
                        iterator.remove();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class Writer implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                UnblockingClientHandler clientHandler;
                while ((clientHandler = registerInWriter.poll()) != null) {
                    try {
                        clientHandler.client.configureBlocking(false);
                        clientHandler.client.register(writerSelector, SelectionKey.OP_WRITE, clientHandler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    writerSelector.select();
                    Set<SelectionKey> selectedKeys = writerSelector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isWritable()) {
                            UnblockingClientHandler unblockingClientHandler = (UnblockingClientHandler) key.attachment();
                            unblockingClientHandler.write();
                        }
                        iterator.remove();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class UnblockingClientHandler {
        private final static int BUFFER_SIZE = 1000000;
        final SocketChannel client;
        private final ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        private final ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        private long currentSizeOfMessageToRead = -1;
        private int readRequests = 0;
        private long startClientTime = -1;
        private long startClientTimeSaved;
        private ByteArrayOutputStream readMessage = new ByteArrayOutputStream();

        UnblockingClientHandler(SocketChannel client) {
            this.client = client;
            writeBuffer.limit(0);
        }

        void read() throws IOException {
            while (client.read(readBuffer) > 0) {
            }
            readBuffer.flip();
            if (startClientTime == -1) {
                startClientTime = System.currentTimeMillis();
            }
            if (readRequests == testConfiguration.getNumberOfRequests()) {
                if (readBuffer.remaining() >= Long.BYTES) {
                    waiterToCompleteAllTests.countDown();
                    long time = readBuffer.getLong();
                    testResults.addRequestTime(time);
                }
                readBuffer.compact();
                return;
            }
            if (currentSizeOfMessageToRead == -1 && readBuffer.remaining() >= Long.BYTES) {
                currentSizeOfMessageToRead = readBuffer.getLong();
            }
            if (currentSizeOfMessageToRead != -1) {
                while (readBuffer.hasRemaining()) {
                    readMessage.write(readBuffer.get());
                }
            }
            if (readMessage.size() == currentSizeOfMessageToRead) {
                readRequests++;
                startClientTimeSaved = startClientTime;
                startClientTime = -1;
                currentSizeOfMessageToRead = -1;
                sortingThreads.submit(() -> {
                    SortArrayProtos.SortArray arrayToSortMessage;
                    try {
                        arrayToSortMessage = SortArrayProtos.SortArray.parseFrom(readMessage.toByteArray());
                        readMessage.reset();
                    } catch (InvalidProtocolBufferException e) {
                        e.printStackTrace();
                        return;
                    }
                    long[] arrayToSort = new long[arrayToSortMessage.getNumberCount()];
                    int i = 0;
                    for (long number : arrayToSortMessage.getNumberList()) {
                        arrayToSort[i++] = number;
                    }

                    long startTime = System.currentTimeMillis();
                    ServerUtils.bubbleSort(arrayToSort);
                    long stopTime = System.currentTimeMillis();
                    long elapsedTime = stopTime - startTime;
                    testResults.addSortTime(elapsedTime);

                    List<Long> sortedList = new LinkedList<>();
                    for (long number : arrayToSort) {
                        sortedList.add(number);
                    }
                    synchronized (writeBuffer) {
                        SortArrayProtos.SortArray sortedArrayMessage = arrayToSortMessage.toBuilder().clearNumber().addAllNumber(sortedList).build();
                        writeBuffer.compact();
                        writeBuffer.putLong(sortedArrayMessage.getSerializedSize());
                        writeBuffer.put(sortedArrayMessage.toByteArray());
                        writeBuffer.flip();
                    }
                    registerInWriter.offer(this);
                    writerSelector.wakeup();
                });
            }
            readBuffer.compact();
        }

        void write() throws IOException {
            if (writeBuffer.hasRemaining()) {
                synchronized (writeBuffer) {
                    client.write(writeBuffer);
                    if (!writeBuffer.hasRemaining()) {
                        System.out.println("WROTE");
                        long stopClientTime = System.currentTimeMillis();
                        long elapsedClientTime = stopClientTime - startClientTimeSaved;
                        testResults.addClientTime(elapsedClientTime);
                    }
                }
            }
        }
    }
}
