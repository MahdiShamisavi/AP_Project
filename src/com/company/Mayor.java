package com.company;

import java.net.Socket;

/**
 * class for Mayor
 * @author MAHDI
 */
public class Mayor extends Citizen implements Action{

    public Mayor(String name, boolean alive) {
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
