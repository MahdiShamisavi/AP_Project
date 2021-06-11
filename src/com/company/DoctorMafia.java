package com.company;

import java.io.*;
import java.net.Socket;

/**
 * class Doctor of Mafia
 * @author MAHDI
 */
public class DoctorMafia extends Mafia implements Action{

    public DoctorMafia(String name, boolean alive) {
        super(name, alive);
    }

    /**
     * Action method for each player
     * @return
     */
    @Override
    public int doAction(Socket socket, BufferedReader bufferedReader) {
        String saveMafia = "";
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("Doctor lector save one person");

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
