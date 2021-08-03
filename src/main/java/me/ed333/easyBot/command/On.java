package me.ed333.easyBot.command;

import me.ed333.easyBot.BOT;
import me.ed333.easyBot.BotMain;
import org.bukkit.command.CommandSender;

import static me.ed333.easyBot.BotMain.cfg;
import static me.ed333.easyBot.Client.isConnected;
import static me.ed333.easyBot.utils.Messages.getMsg;

public class On {
    public On(CommandSender sender) throws Exception {
        if (sender.hasPermission("bot.on")) {
            cfg.set("enable_Bot", true);
            cfg.save(BotMain.INSTANCE.cfgFile);
            if (!isConnected) BOT.initializeBot();
            sender.sendMessage(getMsg("enable_Bot", null));
        } else sender.sendMessage(getMsg("permissionDeny", null));
    }
}
