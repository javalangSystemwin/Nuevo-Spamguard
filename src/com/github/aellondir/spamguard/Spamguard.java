package com.github.aellondir.spamguard;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author James Hull
 * @serial McMod JPGH.0002 class 0 v1
 * @version 0.01
 */
public class Spamguard extends JavaPlugin {
    private final static long serialVersionUID = 6789401536133L;
    private static SpamListener sL;

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static SpamListener getSL() {
        return sL;
    }
}
