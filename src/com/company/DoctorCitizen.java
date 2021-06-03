package com.company;

import java.io.*;
import java.net.Socket;

/**
 * class for doctor of citizen
 */
public class DoctorCitizen extends Citizen implements Action{

    public DoctorCitizen(String name, boolean alive) {
        super(name, alive);
    }

    /**
     * Action method for each player
     * @return
     */
    @Override
    public int doAction(Socket socket) {
        String save = "";
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("save one person");


            InputStream in = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            save = bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(save.trim());

    }
}
