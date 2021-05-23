package com.company;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * class client
 * @author MAHDI
 */
public class Client <T> {
    private int port;

    public Client(int port) {
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket("127.0.0.1", port);

            System.out.println("Connected to the chat server");

            new Reader(socket, this).start();
            new Writer(socket, this).start();

        } catch (IOException ignored) {

        }

    }




}
