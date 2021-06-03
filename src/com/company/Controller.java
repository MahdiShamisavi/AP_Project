package com.company;

import java.net.Socket;

/**
 * Class for Control game
 *
 * @author MAHDI
 */
public class Controller extends Thread {

    private Server server;
    private boolean isNight = true;
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
        //while (!(server.getUserThread().size() % server.getPlayers().size() == 0)){};
        for (UserThread user : server.getUserThread()) {
            System.out.println("this");
            user.sendMessage("Your rol is : " + user.getPlayer().getName());
        }

        int numberAliveMafia = 3;
        int numberAliveCitizen = 7;

        //game loop

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

            //isNight = true;

            if (isNight) {
                // first Night
                if (numberNight == 0) {
                    server.broadcastMafia("Mafia wake up ", null);

                    numberNight++;

                } else {
                    // other nights

                    // Citizen Doctor operation
                    //showAllMembers();
                    int saveCity = doctorCitizen();


                    // mafias Operations
                    server.broadcastMafia("Mafia wake up and chat", null);
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // select the best mafia for shut
                    Player priorityMafia = null;
                    UserThread priorityUser = null;
                    for (UserThread user : server.getUserThread()) {
                        if (user.isMafia()) {
                            if (priorityMafia == null) {
                                priorityMafia = user.getPlayer();
                                priorityUser = user;
                            }
                            if (priorityMafia != null)
                                if (user.getPlayer() instanceof GodFather) {
                                    priorityMafia = user.getPlayer();
                                    priorityUser = user;
                                }
                        }
                    }

                    // mafia shutting
                    showCitizen();
                    assert priorityMafia != null;
                    int purpose = -1;
                    if (priorityMafia instanceof DoctorMafia) {
                        purpose = ((Mafia) priorityMafia).doAction(priorityUser.getSocket());
                    } else {
                        purpose = priorityMafia.doAction(priorityUser.getSocket());

                    }

                    // mafia saving
                    int saveMafia = doctorMafia();

                    // Sniper shutting

                    int sniperPurpose = sniperCitizen();



                }


            } else {


            }


        }


    }

    private int sniperCitizen() {
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer() instanceof Sniper && user.getPlayer().isAlive()) {
                return (user.getPlayer()).doAction(user.getSocket());
            }
        }

        return -1;
    }

    /**
     * method for operation of doctor mafia
     *
     * @return
     */
    private int doctorMafia() {
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer() instanceof DoctorMafia && user.getPlayer().isAlive()) {
                return (user.getPlayer()).doAction(user.getSocket());
            }
        }

        return -1;
    }

    /**
     * method for show all users
     */
    private void showAllMembers(UserThread userThread) {
        int j = 1;
        for (UserThread user : server.getUserThread()) {
            userThread.sendMessage(j + ":" + user.getUsername());
            j++;
        }
    }

    /**
     * doctor operation for city
     *
     * @return
     */
    private int doctorCitizen() {
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer() instanceof DoctorCitizen && user.getPlayer().isAlive()) {
                showAllMembers(user);
                return (user.getPlayer()).doAction(user.getSocket());
            }
        }

        return -1;
    }


    /**
     * show list citizen to mafia
     */
    private void showCitizen() {
        int j = 1;
        for (UserThread user : server.getUserThread()) {
            if (!user.isMafia()) {
                server.broadcastMafia(j + ":" + user.getUsername(), null);
                j++;
            }
        }
    }


}
