package com.company;

/**
 * player class
 * @author MAHDI
 */
public abstract class Player implements Action{
    private String name;
    private boolean alive;

    public Player(String name, boolean alive) {
        this.name = name;
        this.alive = alive;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
