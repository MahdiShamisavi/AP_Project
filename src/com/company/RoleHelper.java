package com.company;

public class RoleHelper {

    private Player player;

    public RoleHelper(Player player) {
        this.player = player;
    }

    public void act(){
        player.doAction();
    }
}
