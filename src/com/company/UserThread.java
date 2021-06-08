package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Observable;

/**
 * class for UserThread
 */
public class UserThread extends Observable implements Runnable {

    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private Player player;
    boolean isMafia = false;
    private String username = "";
    private boolean isCanSpeak = false;
    private int command;
    private boolean getCommand = false;
    private boolean commandReady;

    /**
     * constructor for UserThread
     *
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

            String clientMessage = "";

            do {
                clientMessage = bufferedReader.readLine();
                // send message in day
                if (player.isAlive() && !server.getController().isNight() && isCanSpeak) {
                    System.out.println("day");
                    serverMessage = "[" + username + "]: " + clientMessage;
                    server.broadcast(serverMessage, this);
                } else if
                    // send message in night
                (server.isChatNight() && player.isAlive() && isMafia && server.getController().isNight()) {
                    System.out.println("here");
                    serverMessage = "[" + username + "]: " + clientMessage;
                    server.broadcastMafia(serverMessage, this);
                }
                else if(getCommand){
                    command = Integer.parseInt(clientMessage);
                    this.getCommand = false;
                    this.commandReady = true;
                    notifyObservers(command);
                }

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
        if (player instanceof Mafia)
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

    public String getUsername() {
        return username;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isMafia() {
        return isMafia;
    }

    public boolean isCanSpeak() {
        return isCanSpeak;
    }

    public void setCanSpeak(boolean canSpeak) {
        isCanSpeak = canSpeak;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public boolean isGetCommand() {
        return getCommand;
    }

    public void setGetCommand(boolean getCommand) {
        this.getCommand = getCommand;
    }

    public boolean isCommandReady() {
        return commandReady;
    }

    public void setCommandReady(boolean commandReady) {
        this.commandReady = commandReady;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserThread)) return false;
        UserThread that = (UserThread) o;
        return isMafia == that.isMafia && Objects.equals(socket, that.socket) && Objects.equals(server, that.server) && Objects.equals(writer, that.writer) && Objects.equals(player, that.player) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, server, writer, player, isMafia, username);
    }
}
