package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * class Server
 *
 * @author MAHDI
 */
public class Server {
    private int port;
    private ArrayList<String> userNames;
    private ArrayList<UserThread> userThreads;
    private Controller controller;
    private boolean chatNight = false;


    ArrayList<Player> players = new ArrayList<Player>();



    public Server(int port) {
        this.port = port;
        this.userNames = new ArrayList<String>();
        this.userThreads = new ArrayList<UserThread>();
        controller = new Controller(this);
    }


    /**
     * method for run server and listen
     */
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running");

            // add Player
            addPlayer();
            // shuffle player
            Collections.shuffle(players);

            int j = 0;

            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("new player connected");
                ExecutorService executorService = Executors.newCachedThreadPool();
                UserThread userThread = new UserThread(socket, this , players.get(j));
                executorService.execute(userThread);
                j = j + 1 ;
                if (players.size() == j){
                    System.out.println("game started" );
                    j = 0;
                    //controller.start();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * method for collection players
     */
    private void addPlayer() {
        players.add(new GodFather("GodFather" , true));
        players.add(new DieHard("DieHard" , true));
        players.add(new DoctorCitizen("DoctorCitizen" , true));
        players.add(new DoctorMafia("DoctorMafia" , true));
        players.add(new Sniper("Sniper" , true));
    }

    /**
     * method for broadcast message
     *
     * @param message
     */
    public void broadcast(String message , UserThread userThread) {
        for (UserThread user : userThreads) {
            if (user == userThread)
                continue;
            //System.out.println(user);
            user.sendMessage(message);
        }
    }

    /**
     * Broadcast among Mafia
     * @param message
     * @param userThread
     */
    public void broadcastMafia(String message , UserThread userThread) {
        for (UserThread user : userThreads) {
            if (user == userThread || !user.isMafia)
                continue;
            //System.out.println(user);
            user.sendMessage(message);
        }
    }

    /**
     * add user with checking name
     *
     * @param name
     * @param user
     */
    public boolean addUser(String name, UserThread user) {
        if (userNames.contains(name)) {
            return false;
        }else {
            userThreads.add(user);
            userNames.add(name);
            if (this.getUserThread().size() % this.getPlayers().size() == 0)
                controller.start();
            return true;
        }
    }


    public void setPort(int port) {
        this.port = port;
    }

    public void setUserName(ArrayList<String> userName) {
        this.userNames = userName;
    }

    public void setUserThread(ArrayList<UserThread> userThread) {
        this.userThreads = userThread;
    }

    public int getPort() {
        return port;
    }

    public ArrayList<String> getUserName() {
        return userNames;
    }

    public ArrayList<UserThread> getUserThread() {
        return userThreads;
    }


    public Controller getController() {
        return controller;
    }

    public boolean isChatNight() {
        return chatNight;
    }

    /**
     * getter for UserName
     * @return
     */
    public ArrayList<String> getUserNames() {
        return userNames;
    }

    /**
     * getter for UserThread
     * @return
     */
    public ArrayList<UserThread> getUserThreads() {
        return userThreads;
    }

    /**
     * getter for Player
     * @return
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * method for remove user
     *
     * @param username
     * @param userThread
     */
    public void removeUser(String username, UserThread userThread) {
        boolean removed = userNames.remove(username);
        if (removed) {
            userThreads.remove(userThread);
            System.out.println("The user " + username + " quitted");
        }
    }
}
