package me.ed333.easybot.plugin.with_mirai_api_http.cmd.sub;

import me.ed333.easybot.api.BotAPI;
import org.bukkit.command.CommandSender;

public class Help {
    public Help(CommandSender sender, String[] args) {
        for (String s : BotAPI.getILanguageUtils().getLangList("help")) {
            sender.sendMessage(s);
        }
    }
}
