package com.company;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Writer extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public Writer(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {

        //Console console = System.console();

        Scanner sc = new Scanner(System.in);
        System.out.println("please enter your name");
        String userName = sc.nextLine();
        //client.setUserName(userName);
        writer.println(userName+"\n");

        String text;

        do {
            text = sc.nextLine();
            writer.println(text);

        } while (!text.equals("bye"));

        try {
            socket.close();
        } catch (IOException ex) {

            System.out.println("Error writing to server: " + ex.getMessage());
        }
    }
}
