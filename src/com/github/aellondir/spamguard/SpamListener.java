package com.github.aellondir.spamguard;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author James Hull
 * @serial McMod JPGH.0002 class 2 v1
 * @version 0.01
 */
public class SpamListener implements Listener {

    private int numPlayers = 0;
    /*
     * logArr stores Time at which data was saved the array it was placed into and a string representing the data itself.
     * logArr[0] stores the time which the log entry was made.
     * logArr[1] saves the array the log entry was saved to these two should have the same number of indices throughout the process.
     * logArr[2] saves information related to player logins/outs, issues encountered by the plugin, general claptrap, and debug info.
     * logArr[3] saves information on
     */
    private ArrayList<String> logArr;
    private HashMap<String, SUserChecks> userMap;

    protected SpamListener() {
        this(Bukkit.getServer().getMaxPlayers()/4);
    }

    protected SpamListener(int cap) {
        userMap = new HashMap<>(cap);


    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void addPlayer(PlayerJoinEvent pJE) {
        numPlayers++;
        if (pJE.getPlayer().hasPlayedBefore()) {
            try {
            ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(new FileDescriptor()));
            } catch (IOException e) {
                e.getMessage();
            }
        } else {
            userMap.put(pJE.getPlayer().getPlayerListName(), new SUserChecks(pJE.getPlayer()));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void rmvPlayer(PlayerQuitEvent pQE) {
        numPlayers--;

        SUserChecks sUC = userMap.remove(pQE.getPlayer().getPlayerListName());


    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handleChat(AsyncPlayerChatEvent aPCE) {

        if (aPCE.isAsynchronous() && !aPCE.isCancelled()) {
            int spamC = userMap.get(aPCE.getPlayer().getPlayerListName())
                    .spamCheck(aPCE.getMessage(), aPCE.getPlayer().getWorld().getTime());
            switch (spamC) {
                case -1:
                    return;
                case 0:
                    return;
                case 1:
                    String str = spamed(aPCE.getPlayer(), userMap.get(aPCE.getPlayer()
                            .getPlayerListName()).getNumInfractions(), aPCE.getMessage());
                default:
            }
        }
    }

    private String spamed(Player p, int numInfractions, String msg) {
        StringBuilder sB = new StringBuilder(112);

        sB.append(java.util.Calendar.getInstance().getTime().toString()).append(" ").append(p.getDisplayName()).append(" ").append(Integer.toString(numInfractions));

        switch (numInfractions) {
            case 1:
                //@todo user defined command;
                return sB.append(" ").toString(); //@todo user defined log entry string
            case 2:
                //@todo user defined command
                return sB.append(" ").toString(); //@todo user defined log entry string
            //@todo cases up to 10
            case 10:
                break;
            default:
                if (numInfractions > 10) {
                    //@todo discuss hardcoded perm ban with ex.
                } else {
                    return sB.append(" Error, Numinfractions is either 0 or a non-positive integer.").toString();
                }
        }
        //@todo remove return null;
        return null;
    }
}
