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
    public int doAction(Socket socket) {
        String purpose = "";
        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("kill one person");


            InputStream in = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            purpose = bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(purpose.trim());
    }
}
