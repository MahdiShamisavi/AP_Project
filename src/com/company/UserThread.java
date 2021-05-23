package com.company;

import java.io.*;
import java.net.Socket;

/**
 * class for UserThread
 */
public class UserThread implements Runnable {

    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private RoleHelper role;
    private Player player;
    boolean isMafia = false;
    private String username = "";

    /**
     * constructor for UserThread
     * @param socket
     * @param server
     * @param player
     */
    public UserThread(Socket socket, Server server, Player player) {
        this.socket = socket;
        this.server = server;
        this.player = player;
    }


    /**
     * method run for every thread
     */
    @Override
    public void run() {

        // set boolean Mafia
        setBoolMafia();

        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            writer = new PrintWriter(out, true);

            // get user name

            boolean check;
            do {
                username = bufferedReader.readLine();
                check = server.addUser(username, this);
                if (!check) {
                    this.sendMessage("this name exist please try another one");
                } else {
                    this.sendMessage("you connected to game");
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
                // send message in day
                if (player.isAlive() && !server.getController().isNight())
                    server.broadcast(serverMessage, this);
                // send message in night
                if (player.isAlive() && isMafia && server.getController().isNight())
                    server.broadcastMafia(serverMessage, this);

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

    // check for mafia

    /**
     * check for mafia
     */
    private void setBoolMafia() {
        if (player instanceof Mafia )
            isMafia = true;
    }

    /**
     * method for send message to client
     *
     * @param str
     */
    public void sendMessage(String str) {
        writer.println(str);
    }


    public Socket getSocket() {
        return socket;
    }

    public Server getServer() {
        return server;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public RoleHelper getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isMafia() {
        return isMafia;
    }
}
