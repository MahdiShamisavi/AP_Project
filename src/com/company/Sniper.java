package com.company;

/**
 * class for Sniper
 * @author MAHDI
 */
public class Sniper extends Citizen implements Action{

    public Sniper(String name, boolean alive) {
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
