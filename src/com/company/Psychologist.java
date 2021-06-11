package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
    public int doAction(Socket socket, BufferedReader bufferedReader) {
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("silent one person ");

            super.purpose = -1;

//            InputStream in = socket.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            try {
                Thread.sleep(20000);
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
