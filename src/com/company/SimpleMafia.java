package com.company;

import java.net.Socket;

/**
 * class for simple Mafia
 */
public class SimpleMafia extends Mafia implements Action{

    public SimpleMafia(String name, boolean alive) {
        super(name, alive);
    }

    /**
     * Action method for each player
     */
    @Override
    public int doAction(Socket socket) {
        return (super.doAction(socket));

    }
}
