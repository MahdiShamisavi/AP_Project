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
            do {
                username = bufferedReader.readLine();
            }
            while (!server.addUser(username, this));

            //get message from client




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * method for send message to client
     * @param str
     */
    public void sendMessage(String str){
        writer.println(str);
    }
}
