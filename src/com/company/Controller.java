package com.company;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Class for Control game
 *
 * @author MAHDI
 */
public class Controller extends Thread implements Observer {

    private Server server;
    private boolean isNight = true;
    private int numberNight = 0;
    private final ArrayList<UserThread> citizen;
    private final ArrayList<UserThread> allUser;
    private final ArrayList<UserThread> mafias;
    private boolean goOn;

    public Controller(Server server) {
        this.server = server;
        citizen = new ArrayList<UserThread>();
        allUser = new ArrayList<UserThread>();
        mafias =  new ArrayList<UserThread>();
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
                    isNight = false;

                } else {
                    // other nights

                    // Citizen Doctor operation
                    //showAllMembers();
                    UserThread saveCity = doctorCitizen();


                    // mafias Operations

                    // chat Mafia in night
//                    server.setChatNight(true);
//                    server.broadcastMafia("Mafia wake up and chat", null);
//
//                    try {
//                        sleep(50000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    //server.setChatNight(false);


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
                    UserThread shutMafia = null;
                    if (purpose != -1)
                        shutMafia = citizen.get(purpose -1);

                    // mafia saving
                    UserThread saveMafia = doctorMafia();

                    // Sniper shutting

                    UserThread sniperPurpose = sniperCitizen();



                    if(!shutMafia.equals(saveCity)){
                       shutMafia.getPlayer().setAlive(false);
                    }

                    if(sniperPurpose != null && !sniperPurpose.equals(saveMafia)){
                        sniperPurpose.getPlayer().setAlive(false);
                    }

                }




                isNight = false;
            } else {

                speakDay();


                isNight = true;

            }


        }


    }

    /**
     * method for speaking in day
     */
    private void speakDay() {
        for (UserThread user : server.getUserThread()){
            user.sendMessage("Please speak");
            user.setCanSpeak(true);
        }

        try {
            sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (UserThread user : server.getUserThread()){
            user.sendMessage("end speaking");
            user.setCanSpeak(false);
        }

    }

    /**
     * method for sniper
     *
     * @return
     */
    private UserThread sniperCitizen() {
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer() instanceof Sniper && user.getPlayer().isAlive()) {
                showAllMembers(user);
                goOn = false;
                user.setGetCommand(true);
                while(!goOn);
                int k = user.getCommand();
                if (k == -1)
                    return null;
                return (allUser.get(k-1));
            }
        }

        return null;
    }

    /**
     * method for operation of doctor mafia
     *
     * @return
     */
    private UserThread doctorMafia() {
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer() instanceof DoctorMafia && user.getPlayer().isAlive()) {
                showMafia();
                goOn = false;
                user.setGetCommand(true);
                while(!goOn);
                return (mafias.get(user.getCommand()-1));
            }
        }

        return null;
    }

    /**
     * method for show all users
     */
    private void showAllMembers(UserThread userThread) {
        int j = 1;
        allUser.clear();
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer().isAlive()) {
                userThread.sendMessage(j + ":" + user.getUsername());
                allUser.add(user);
                j++;
            }
        }
    }

    /**
     * doctor operation for city
     *
     * @return
     */
    private UserThread doctorCitizen() {
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer() instanceof DoctorCitizen && user.getPlayer().isAlive()) {
                showAllMembers(user);
                goOn = false;
                user.setGetCommand(true);
                while(!goOn);
                return (allUser.get(user.getCommand()-1));
            }
        }

        return null;
    }


    /**
     * show list citizen to mafia
     */
    private void showCitizen() {
        int j = 1;
        citizen.clear();
        for (UserThread user : server.getUserThread()) {
            if (!user.isMafia() && user.getPlayer().isAlive()) {
                server.broadcastMafia(j + ":" + user.getUsername(), null);
                citizen.add(user);
                j++;
            }
        }
    }


    /**
     * method for show mafia to doctor lector
     */
    private void showMafia(){
        int j = 1;
        mafias.clear();
        for (UserThread user : server.getUserThread()) {
            if (user.isMafia() && user.getPlayer().isAlive()) {
                server.broadcastMafia(j + ":" + user.getUsername(), null);
                mafias.add(user);
                j++;
            }
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        goOn = true;
    }
}
