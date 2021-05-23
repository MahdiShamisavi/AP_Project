package com.company;

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
    public int doAction() {

        return 0;
    }
}
