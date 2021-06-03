package com.company;

import java.net.Socket;

/**
 * class for Simple Citizen
 * @author MAHDI
 */
public class SimpleCitizen extends Citizen implements Action{

    public SimpleCitizen(String name, boolean alive) {
        super(name, alive);
    }

    /**
     * Action method for each player
     * @return
     */
    @Override
    public int doAction(Socket socket) {

        return 0;
    }
}
