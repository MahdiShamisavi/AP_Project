package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 * class for doctor of citizen
 */
public class DoctorCitizen extends Citizen implements Action, Observer {

    private String save = "";

    public DoctorCitizen(String name, boolean alive) {
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
            writer.println("save one person");
            save = "";
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

    @Override
    public void update(Observable o, Object arg) {
        purpose = (Integer) arg;
    }
}
