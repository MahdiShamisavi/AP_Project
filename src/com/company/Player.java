package com.company;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return alive == player.alive && Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alive);
    }
}
