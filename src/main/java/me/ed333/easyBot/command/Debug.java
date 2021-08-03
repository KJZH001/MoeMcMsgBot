package me.ed333.easyBot.command;

import me.ed333.easyBot.BotMain;
import org.bukkit.command.CommandSender;

import static me.ed333.easyBot.BOT.enableBot_Players;
import static me.ed333.easyBot.BotMain.hasPAPI;
import static me.ed333.easyBot.Client.isConnected;
import static me.ed333.easyBot.utils.Messages.getMsg;

public class Debug {
    public Debug(CommandSender sender) {
        if (sender.hasPermission("bot.debug")) {
            sender.sendMessage("EasyBot-" + BotMain.pluginVer + " | DEBUG LOG: ");
            sender.sendMessage("    enableBot_Players: " + enableBot_Players.toString());
            sender.sendMessage("    connect: " + isConnected);
            sender.sendMessage("    has PAPI: " + hasPAPI);
        } else sender.sendMessage(getMsg("permissionDeny", null));
    }
}
