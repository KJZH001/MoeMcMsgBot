package me.ed333.easyBot.command;

import me.ed333.easyBot.BotMain;
import org.bukkit.command.CommandSender;

import java.io.IOException;

import static me.ed333.easyBot.BOT.close;
import static me.ed333.easyBot.Client.isConnected;
import static me.ed333.easyBot.utils.Messages.getMsg;

public class Off {
    public Off(CommandSender sender) throws IOException {
        if (sender.hasPermission("bot.off")) {
            BotMain.cfg.set("enable_Bot", false);
            BotMain.cfg.save(BotMain.INSTANCE.cfgFile);
            if (isConnected) close();
            sender.sendMessage(getMsg("disable_Bot", null));
        } else sender.sendMessage(getMsg("permissionDeny", null));
    }
}
