package me.ed333.easybot.api.messages;

import me.ed333.easybot.api.EConfigKeys;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class DEBUG {
    private static final ConsoleCommandSender sender = Bukkit.getConsoleSender();

    public static void debugInfo(String msg) {
        if (EConfigKeys.DEBUG.getBoolean()) {
           sender.sendMessage("[§3BOT_DEBUG§f] §aFINE §f| §3" + msg);
        }
    }

    public static void debugWarn(String msg) {
        if (EConfigKeys.DEBUG.getBoolean()) {
            System.out.println("[§3BOT_DEBUG§f] §aWARN §f| §6" + msg);
        }
    }
}
