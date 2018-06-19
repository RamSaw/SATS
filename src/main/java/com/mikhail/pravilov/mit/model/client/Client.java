package com.mikhail.pravilov.mit.model.client;

import com.mikhail.pravilov.mit.model.protocol.SortArrayProtos;
import org.apache.commons.io.input.BoundedInputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Client implements Runnable {
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private final int numberOfElements;
    private final int numberOfRequests;
    private final int timeBetweenRequests;

    Client(String hostname, int port, int numberOfElements, int numberOfRequests, int timeBetweenRequests) throws IOException {
        this.numberOfElements = numberOfElements;
        this.numberOfRequests = numberOfRequests;
        this.timeBetweenRequests = timeBetweenRequests;
        socket = new Socket(InetAddress.getByName(hostname), port);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        long totalTime = 0;
        Random rand = new Random();
        for (int i = 0; i < numberOfRequests; i++) {
            long startTime = System.currentTimeMillis();
            Long[] arrayToSort = new Long[numberOfElements];
            for (int j = 0; j < numberOfElements; j++) {
                arrayToSort[j] = rand.nextLong();
            }

            SortArrayProtos.SortArray sortArray;
            try {
                SortArrayProtos.SortArray arrayToSortMessage = SortArrayProtos.SortArray.newBuilder().addAllNumber(Arrays.asList(arrayToSort)).build();
                dataOutputStream.writeLong(arrayToSortMessage.getSerializedSize());
                arrayToSortMessage.writeTo(dataOutputStream);
                long size = dataInputStream.readLong();
                sortArray = SortArrayProtos.SortArray.parseFrom(new BoundedInputStream(dataInputStream, size));
            } catch (IOException e) {
                throw new IllegalStateException("IO problems", e);
            }
            Long[] sortedArray = new Long[sortArray.getNumberCount()];
            sortArray.getNumberList().toArray(sortedArray);
            Arrays.sort(arrayToSort);
            if (!Arrays.equals(sortedArray, arrayToSort)) {
                throw new IllegalStateException("Array is not sorted!");
            }
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            totalTime += elapsedTime;
            try {
                sleep(timeBetweenRequests);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            // TODO: decide what formula
            // dataOutputStream.writeLong(elapsedTime - numberOfRequests * timeBetweenRequests);
            // dataOutputStream.writeLong(elapsedTime);
            dataOutputStream.writeLong(totalTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
