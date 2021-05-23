package com.company;

/**
 * Class for Control game
 *
 * @author MAHDI
 */
public class Controller extends Thread {

    private Server server;
    private boolean isNight = false;
    private int numberNight = 0;

    public Controller(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    public boolean isNight() {
        return isNight;
    }

    public int getNumberNight() {
        return numberNight;
    }


    @Override
    public void run() {
        // send rol to every one
        for (UserThread user : server.getUserThread()) {
            user.sendMessage("Your rol is : " + user.getPlayer().getName());
        }

        int numberAliveMafia = 3;
        int numberAliveCitizen = 7;
        while (numberAliveCitizen > numberAliveMafia) {
            numberAliveMafia = 0;
            numberAliveCitizen = 0;
            // check for ending game
            for (UserThread user : server.getUserThread()) {
                if (user.isMafia() && user.getPlayer().isAlive())
                    numberAliveMafia++;
                if (!user.isMafia && user.getPlayer().isAlive())
                    numberAliveCitizen++;

            }


            // sleep for players see each other
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            isNight = true;

            if (isNight) {
                // first Night
                if (numberNight == 0) {
                    server.broadcastMafia("Mafia wake up ", null);

                    numberNight++;

                } else {

                    server.broadcastMafia("Mafia wake up and kill", null);
                    Player priorityMafia = null;
                    for (UserThread user : server.getUserThread()) {
                        if (user.isMafia()) {
                            if (priorityMafia == null)
                                priorityMafia = user.getPlayer();
                            if (priorityMafia != null)
                                if (user.getPlayer() instanceof GodFather)
                                    priorityMafia = user.getPlayer();
                        }
                    }

                    showCitizen();
                    assert priorityMafia != null;
                    int purpose = priorityMafia.doAction();



                }


            } else {


            }


        }


    }


    /**
     * show list citizen to mafia
     */
    private void showCitizen() {
        int j = 1;
        for (UserThread user : server.getUserThread()) {
            if (!user.isMafia()) {
                server.broadcastMafia(j + ":" + user.getUsername(), null);
            }
        }
    }

}
