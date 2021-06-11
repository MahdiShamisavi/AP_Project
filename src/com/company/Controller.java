package com.company;

import java.net.Socket;
import java.util.*;

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
    private HashMap<UserThread, Integer> hashCommand;
    UserThread psychologistPurpose = null;

    public Controller(Server server) {
        this.server = server;
        citizen = new ArrayList<UserThread>();
        allUser = new ArrayList<UserThread>();
        mafias = new ArrayList<UserThread>();
        hashCommand = new HashMap<>();
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
                // Mafia Know Role each other
                if (numberNight == 0) {

                    server.broadcastMafia("Night started" , null);
                    server.broadcastMafia("Mafia wake up ", null);
                    for (UserThread user : server.getUserThread()) {
                        if (user.isMafia() && user.getPlayer().isAlive()) {
                            server.broadcastMafia(user.getUsername() + "===" + user.getPlayer().getName(), null);
                        }
                    }
                    numberNight++;
                    isNight = false;

                } else {
                    server.broadcastMafia("Night started" , null);
                    // other nights

                    // Citizen Doctor operation
                    //showAllMembers();
                    UserThread saveCity = doctorCitizen();


                    // mafias Operations

                    // chat Mafia in night
                    server.setChatNight(true);
                    server.broadcastMafia("Mafia wake up and chat with other", null);

                    try {
                        sleep(50000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    server.setChatNight(false);


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
                    priorityUser.setGetCommand(true);
                    int purpose = -1;
                    if (priorityMafia instanceof DoctorMafia) {
                        purpose = ((Mafia) priorityMafia).doAction(priorityUser.getSocket(), priorityUser.getBufferedReader());
                    } else {
                        purpose = priorityMafia.doAction(priorityUser.getSocket(), priorityUser.getBufferedReader());

                    }
                    UserThread shutMafia = null;
                    if (purpose != -1)
                        shutMafia = citizen.get(purpose - 1);

                    // mafia saving
                    UserThread saveMafia = doctorMafia();

                    // Sniper shutting

                    UserThread sniperPurpose = sniperCitizen();

                    // Psychologist purpose
                    psychologistPurpose = psychologist();

                    //Detective Operation
                    detective();


                    // kill shut mafia in night
                    if (!shutMafia.equals(saveCity) && !(shutMafia.getPlayer() instanceof DieHard)) {
                        shutMafia.getPlayer().setAlive(false);
                        server.broadcast(shutMafia.getUsername() + " is Killed", null);
                    }

                    // kill shut sniper
                    if (sniperPurpose != null && !sniperPurpose.equals(saveMafia)) {
                        if (sniperPurpose.getPlayer() instanceof Mafia) {
                            sniperPurpose.getPlayer().setAlive(false);
                            server.broadcast(sniperPurpose.getUsername() + " is Killed", null);
                        } else {
                            for (UserThread user : server.getUserThread()) {
                                if (user.getPlayer() instanceof Sniper) {
                                    user.getPlayer().setAlive(false);
                                    server.broadcast(sniperPurpose.getUsername() + " is Killed", null);
                                }
                            }

                        }
                    }


                    //DieHard Operation
                    dieHard();

                }


                isNight = false;
            } else {

                server.broadcastMafia("Day started" , null);
                speakDay();

                // vote in day
                Vote vote = new Vote(server);
                HashMap<UserThread, Integer> result = vote.voting();
                Map.Entry<UserThread, Integer> maxVote = vote.maxVote(result);
                System.out.println(maxVote.getKey());

                //kill max vote
                UserThread killDay = maxVote.getKey();

                // mayor decision
                int mayorDecision = mayor();

                if (mayorDecision != 1) {
                    killDay.getPlayer().setAlive(false);
                    server.broadcast(killDay.getUsername() + " is Killed", null);
                } else {
                    server.broadcast("mayor cancel voting", null);
                }


                isNight = true;

            }


        }


        System.out.println("Game ended");


    }


    /**
     * method for speaking in day
     */
    private void speakDay() {
        for (UserThread user : server.getUserThread()) {
            user.sendMessage("Please speak , this is your turn");
            if (!user.equals(psychologistPurpose)) {
                user.setCanSpeak(true);
                try {
                    sleep(25000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                user.setCanSpeak(false);
                user.sendMessage("your turn finish");
            }
        }


//        for (UserThread user : server.getUserThread()) {
//            user.sendMessage("end speaking");
//            user.setCanSpeak(false);
//        }

    }

    /**
     * method for dieHard Operation
     */
    private void dieHard() {
        int k = -1;
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer() instanceof DieHard && user.getPlayer().isAlive()) {
                user.setGetCommand(true);
                k = user.getPlayer().doAction(user.getSocket(), user.getBufferedReader());
                if (k == 1) {
                    server.broadcast("Role removed : ", null);
                    for (UserThread u : server.getUserThread()) {
                        if (!u.getPlayer().isAlive()) {
                            server.broadcast(u.getPlayer().getName(), null);
                        }
                    }
                }
            }
        }
    }

    /**
     * method for detective operation
     */
    private void detective() {
        int k = -1;
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer() instanceof Detective && user.getPlayer().isAlive()) {
                showAllMembers(user);
                user.setGetCommand(true);
                k = user.getPlayer().doAction(user.getSocket(), user.getBufferedReader());
                if ( k != -1){
                    UserThread temp = allUser.get(k - 1);
                    if (temp.getPlayer() instanceof Citizen || temp.getPlayer() instanceof GodFather) {
                        user.sendMessage("Negative");
                    } else {
                        user.sendMessage("Positive");
                    }

                }
            }
        }
    }


    /**
     * method for mayor operation
     *
     * @return
     */
    private int mayor() {
        int k = -1;
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer() instanceof Mayor && user.getPlayer().isAlive()) {

                user.setGetCommand(true);
                k = user.getPlayer().doAction(user.getSocket(), user.getBufferedReader());

            }
        }

        return k;
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
//                goOn = false;
                user.setGetCommand(true);
//
//                int k = user.getCommand();
                int k = user.getPlayer().doAction(user.getSocket(), user.getBufferedReader());
                if (k == -1)
                    return null;
                return (allUser.get(k - 1));
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
//                goOn = false;
                user.setGetCommand(true);

                int p = user.getPlayer().doAction(user.getSocket(), user.getBufferedReader());
                if (p != -1)
                    return (mafias.get(p - 1));
            }
        }

        return null;
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
//                goOn = false;
                user.setGetCommand(true);

                int p = user.getPlayer().doAction(user.getSocket(), user.getBufferedReader());
                if (p != -1)
                    return (allUser.get(p - 1));
            }
        }

        return null;
    }

    /**
     * method for Psychologist
     *
     * @return
     */
    private UserThread psychologist() {
        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer() instanceof Psychologist && user.getPlayer().isAlive()) {
                showAllMembers(user);
//                goOn = false;
                user.setGetCommand(true);

                int p = user.getPlayer().doAction(user.getSocket(), user.getBufferedReader());
                if (p != -1)
                    return (allUser.get(p - 1));
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
    private void showMafia() {
        int j = 1;
        mafias.clear();
        server.broadcastMafia("mafias : ", null);
        for (UserThread user : server.getUserThread()) {
            if (user.isMafia() && user.getPlayer().isAlive()) {
                server.broadcastMafia(j + ":" + user.getUsername(), null);
                mafias.add(user);
                j++;
            }
        }
    }


    /**
     * get command from UserThread
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        HashMap<UserThread, Integer> temp = new HashMap<>();
        temp = (HashMap<UserThread, Integer>) arg;
        for (Map.Entry e : temp.entrySet())
            hashCommand.put((UserThread) e.getKey(), (Integer) e.getValue());

    }
}
