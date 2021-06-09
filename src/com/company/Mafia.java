package com.company;

import java.io.*;
import java.net.Socket;

/**
 * class for Mafia group
 * @author MAHDI
 */
public class Mafia extends Player implements Action{

    public Mafia(String name, boolean alive) {
        super(name, alive);
    }

    @Override
    public int doAction(Socket socket, BufferedReader bufferedReader) {
        //String purpose = "";
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("kill one person");
            super.purpose = -1;

//            InputStream in = socket.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            try {
                Thread.sleep(15000);
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
