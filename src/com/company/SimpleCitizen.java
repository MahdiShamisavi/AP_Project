package com.company;

/**
 * class for Simple Citizen
 * @author MAHDI
 */
public class SimpleCitizen extends Citizen implements Action{

    public SimpleCitizen(String name, boolean alive) {
        super(name, alive);
    }

    /**
     * Action method for each player
     */
    @Override
    public void doAction() {

    }
}
