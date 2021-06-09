package com.company;

import java.io.BufferedReader;
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
    public int doAction(Socket socket, BufferedReader bufferedReader) {
        return (super.doAction(socket , bufferedReader));

    }
}
