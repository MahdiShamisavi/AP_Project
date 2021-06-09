package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * class for DieHard
 *
 * @author MAHDI
 */
public class DieHard extends Citizen implements Action {

    public DieHard(String name, boolean alive) {
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
            writer.println("pleas enter 1 if you want show removed Role");

            super.purpose = -1;


            try {
                Thread.sleep(10000);
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
