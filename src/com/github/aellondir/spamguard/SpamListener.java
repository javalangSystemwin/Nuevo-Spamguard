package com.github.aellondir.spamguard;

import java.util.ArrayList;
import java.util.HashMap;
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
    private ArrayList<String> stepsTaken;
    private HashMap<String, SUserChecks> userMap;

    protected SpamListener() {
        //this();
    }

    protected SpamListener(int cap) {
        userMap = new HashMap<>(cap);

        stepsTaken = new ArrayList<>();
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void addPlayer(PlayerJoinEvent pJE) {
        numPlayers++;

        userMap.put(pJE.getPlayer().getPlayerListName(), new SUserChecks(pJE.getPlayer()));
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void rmvPlayer(PlayerQuitEvent pQE) {
        numPlayers--;

        SUserChecks sUC = userMap.remove(pQE.getPlayer().getPlayerListName());


    }

    @EventHandler(priority= EventPriority.HIGHEST)
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
                            .getPlayerListName()).getNumInfractions());
                default:
            }
        }
    }

    private String spamed(Player p, int numInfractions) {
        return "boo";
    }
}
