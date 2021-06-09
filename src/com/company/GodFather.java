package com.company;

import java.io.BufferedReader;
import java.net.Socket;

/**
 * class for GodFather of Mafia
 * @author MAHDI
 */
public class GodFather extends Mafia implements Action{

    public GodFather(String name, boolean alive) {
        super(name, alive);
    }

    /**
     * Action method for each player
     * @return
     */
    @Override
    public int doAction(Socket socket, BufferedReader bufferedReader) {
        return (super.doAction(socket,bufferedReader));

    }
}
