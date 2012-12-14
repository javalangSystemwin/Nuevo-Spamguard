package com.github.aellondir.spamguard;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.entity.Player;

/**
 *
 * @author James Hull
 * @serial McMod JPGH.0001
 * @version 0.01
 */
public class SUserChecks {

    private transient long timeAtLastMsg;
    private transient ArrayList<String> recMsgs;
    private int numInfractions = 0;
    private final int COOL_DOWN = 300000;
    private final Random rand = new Random(System.nanoTime());
    private final static String PLACE_HOLDER = "Place Holder";

    protected SUserChecks(Player p) {
        this(p, PLACE_HOLDER);
    }

    protected SUserChecks(Player p, String msg) {
        timeAtLastMsg = p.getWorld().getFullTime();

        recMsgs = new ArrayList<>();

        recMsgs.add(msg);
    }

    protected int spamCheck(String msg, long l) {
        if (msg == null) {
            return 0;
        }

        int index = recMsgs.indexOf(PLACE_HOLDER);

        if (index >= 0) {
            recMsgs.remove(index);
        }

        if (l - timeAtLastMsg >  COOL_DOWN + rand.nextInt(60000) - numInfractions * 1000) {
            if (recMsgs.size() < 5 + (numInfractions - 1)) {
                recMsgs.add(msg);
            } else {
                recMsgs.remove(0);
                recMsgs.add(msg);
            }

            timeAtLastMsg = l;

            return -1;
        }

        if (this.isSpamming(msg)) {
            numInfractions += 1;

            if (recMsgs.size() < 5 + (numInfractions - 1)) {
                recMsgs.add(msg);
            } else {
                recMsgs.remove(0);
                recMsgs.add(msg);
            }

            timeAtLastMsg = l;

            return 1;
        } else {
            if (recMsgs.size() < 5 + (numInfractions - 1)) {
                recMsgs.add(msg);
            } else {
                recMsgs.remove(0);
                recMsgs.add(msg);
            }

            timeAtLastMsg = l;
        }

        return 0;
    }

    private boolean isSpamming(String msg) {
        for (String str : recMsgs) {
            if (str.equals(msg)) {
                return true;
            }
        }

        return false;
    }

    public int getNumInfractions() {
        return numInfractions;
    }
}
