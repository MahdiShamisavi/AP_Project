package com.company;

import java.io.BufferedReader;
import java.net.Socket;

/**
 * interface Action
 */
public interface Action {

    /**
     * do action for every player
     */
    public int  doAction(Socket socket , BufferedReader bufferedReader);
}
