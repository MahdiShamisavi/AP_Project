package com.company;

import java.io.BufferedReader;
import java.net.Socket;

/**
 * class for DieHard
 * @author MAHDI
 */
public class DieHard extends Citizen implements Action{

    public DieHard(String name, boolean alive) {
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
