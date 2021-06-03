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
    public int doAction(Socket socket) {
        String saveMafia = "";
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("Doctor lector save one person");


            InputStream in = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            saveMafia = bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(saveMafia.trim());
    }
}
