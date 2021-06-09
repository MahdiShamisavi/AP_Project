package com.company;

import java.io.BufferedReader;
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
    public int doAction(Socket socket, BufferedReader bufferedReader) {

        return 0;
    }
}
