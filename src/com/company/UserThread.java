package com.company;

import java.io.*;
import java.net.Socket;

/**
 * class for UserThread
 */
public class UserThread implements Runnable {

    private Socket socket;
    private Server server;
    PrintWriter writer;

    public UserThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }


    /**
     * method run for every thread
     */
    @Override
    public void run() {

        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            writer = new PrintWriter(out, true);

            // get user name
            String username = "";
            boolean check ;
            do {
                username = bufferedReader.readLine();
                check = server.addUser(username, this);
                if (!check) {
                    this.sendMessage("this name exist please try another one");
                } else {
                    this.sendMessage("you conected to game");
                }
            }
            while (!check);

            //get message from client
            String serverMessage = "New user connected: " + username;
            server.broadcast(serverMessage, this);

            String clientMessage;

            do {
                clientMessage = bufferedReader.readLine();
                serverMessage = "[" + username + "]: " + clientMessage;
                server.broadcast(serverMessage, this);

            } while (!clientMessage.equals("exit"));

            server.removeUser(username, this);
            socket.close();

            serverMessage = username + " has quitted.";
            server.broadcast(serverMessage, this);

        } catch (IOException ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }


    }

    /**
     * method for send message to client
     *
     * @param str
     */
    public void sendMessage(String str) {
        writer.println(str);
    }
}
