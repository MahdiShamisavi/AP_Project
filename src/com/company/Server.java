package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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

    public Server(int port) {
        this.port = port;
        this.userNames = new ArrayList<String>();
        this.userThreads = new ArrayList<UserThread>();
    }


    /**
     * method for run server and listen
     */
    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("new player connected");
                ExecutorService executorService = Executors.newCachedThreadPool();
                UserThread userThread = new UserThread(socket, this);
                executorService.execute(userThread);
                userThreads.add(userThread);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * method for broadcast message
     * @param message
     */
    public void broadcast(String message){
        for(UserThread user : userThreads){
            user.sendMessage(message);
        }
    }

    /**
     * add user with checking name
     * @param name
     * @param user
     */
    public boolean addUser(String name ,UserThread user){
        if (userNames.contains(name)){
            return false;
        }
        userThreads.add(user);
        userNames.add(name);
        return true;
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
}
