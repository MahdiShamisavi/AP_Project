package com.company;

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
    public int doAction() {

        return 0;
    }
}
