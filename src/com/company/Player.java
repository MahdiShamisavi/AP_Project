package com.company;

/**
 * player class
 * @author MAHDI
 */
public abstract class Player {
    private String name;
    private boolean alive;

    public Player(String name, boolean alive) {
        this.name = name;
        this.alive = alive;
    }


}
