package me.ed333.easyBot.command;

import org.bukkit.command.CommandSender;

import static me.ed333.easyBot.utils.Messages.getList;

public class Help {
    public Help(CommandSender sender) {
        for (String s : getList("help")) sender.sendMessage(s);
    }
}
