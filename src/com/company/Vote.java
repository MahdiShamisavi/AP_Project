package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * class for vote
 *
 * @author MAHDI
 */
public class Vote {
    private Server server;
    private HashMap<UserThread, Integer> voteResult;

    /**
     * constructor for vote class
     *
     * @param server
     */
    public Vote(Server server) {
        this.server = server;
        voteResult = new HashMap<UserThread, Integer>();
    }

    /**
     * method for get vote from user
     *
     * @return
     */
    public HashMap<UserThread, Integer> voting() {

        for (UserThread user : server.getUserThread()) {
            if (user.getPlayer().isAlive()) {
                for (UserThread u : server.getUserThreads()) {
                    u.setGetVote(true);
                    u.setVote(0);
                }
                server.broadcast("please vote for : " + user.getUsername() + "\n 0 == neg  , 1 == pos", null);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int numVote = 0;
                for (UserThread t : server.getUserThread()) {
                    numVote = +t.getVote();
                }
                voteResult.put(user, numVote);

            }
        }
        for (UserThread u : server.getUserThreads()) {
            u.setGetVote(false);
            u.setVote(0);
        }


        return voteResult;
    }

    /**
     * method for find the maximum vote
     * @param map
     * @return
     */
    public Map.Entry<UserThread, Integer> maxVote (HashMap<UserThread, Integer> map)  {
        Map.Entry<UserThread, Integer> maxEntry = null;

        for (Map.Entry<UserThread, Integer> entry : map.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        return maxEntry;
    }
}
