package com.company;

import java.net.Socket;

/**
 * class for psychologist
 * @author MAHDI
 */
public class Psychologist extends Citizen implements Action{

    public Psychologist(String name, boolean alive) {
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
