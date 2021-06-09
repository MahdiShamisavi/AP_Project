package com.company;

import java.io.*;
import java.net.Socket;

/**
 * class for Sniper
 * @author MAHDI
 */
public class Sniper extends Citizen implements Action{

    public Sniper(String name, boolean alive) {
        super(name, alive);
    }

    /**
     * Action method for each player
     * @return
     */
    @Override
    public int doAction(Socket socket, BufferedReader bufferedReader) {
        String sniper = "";
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("shut one person otherwise print -1");

            super.purpose = -1;

//            InputStream in = socket.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            purpose = super.getPurpose();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return purpose;
    }
}
