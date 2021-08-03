package me.ed333.easyBot.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.ed333.easyBot.BOT.enableBot_Players;
import static me.ed333.easyBot.BotMain.cfg;
import static me.ed333.easyBot.utils.Messages.getMsg;

public class Disable {
    public Disable(CommandSender sender) {
        Player p = (Player) sender;
        UUID uid = p.getUniqueId();
        enableBot_Players.remove(p);
        cfg.set(uid.toString() + ".enable_Bot", false);
        sender.sendMessage(getMsg("player_disableBot", null));
    }
}
