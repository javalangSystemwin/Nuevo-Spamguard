package com.github.aellondir.spamguard;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.Server;
import org.bukkit.entity.Player;

/**
 *
 * @author James Hull
 * @serial McMod JPGH.0002 class 2 v1
 * @version 0.01
 */
public class SpamListener {

    private transient World w;
    private HashMap<Player, SUserChecks> users = new HashMap<>();

    public SpamListener() {
    }

}
