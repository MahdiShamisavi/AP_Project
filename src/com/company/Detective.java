package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Detective extends Citizen implements Action {

    public Detective(String name, boolean alive) {
        super(name, alive);
    }

    @Override
    public int doAction(Socket socket, BufferedReader bufferedReader) {

        try {
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println("pleas enter number of person that you want Inquire");

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
