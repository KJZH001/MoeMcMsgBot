package me.ed333.easybot.api.messages;

import me.ed333.easybot.api.EConfigKeys;

public class DEBUG {
    public static void debugInfo(String msg) {
        if (EConfigKeys.DEBUG.getBoolean()) {
           System.out.println("BOT_[DEBUG_INFO] " + msg);
        }
    }

    public static void debugWarn(String msg) {
        if (EConfigKeys.DEBUG.getBoolean()) {
            System.out.println("BOT_[DEBUG_WARN] " + msg);
        }
    }
}
