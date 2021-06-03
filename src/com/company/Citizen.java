package com.company;

import java.net.Socket;

/**
 * class for citizen
 * @author MAHDI
 */
public class Citizen extends Player{

    public Citizen(String name, boolean alive) {
        super(name, alive);
    }


    @Override
    public int doAction(Socket socket) {
        return 0;
    }
}
