package com.company;

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
    public int doAction(Socket socket) {
        return (super.doAction(socket));

    }
}
