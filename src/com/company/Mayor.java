package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * class for Mayor
 *
 * @author MAHDI
 */
public class Mayor extends Citizen implements Action {

    public Mayor(String name, boolean alive) {
        super(name, alive);
    }

    /**
     * Action method for each player
     *
     * @return
     */
    @Override
    public int doAction(Socket socket, BufferedReader bufferedReader) {
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("would you cancel voting if you want please print 1");

            super.purpose = -1;


            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            purpose = super.getPurpose();


        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return purpose;
    }
}
